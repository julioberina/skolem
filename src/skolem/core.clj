(ns skolem.core
  (:gen-class))

(defn next-term [terms k coefficients]
  (apply +' (map (fn [x] (eval (conj x '*')))
  (partition 2 (interleave (nthrest (reverse terms) (dec k)) coefficients)))))

(defn shift-terms [terms num] (conj (vec (rest terms)) num))

(defn lrr [terms k & coefficients]
  "Generates a linear recurrence relation using initial terms, k, and optional coefficients.\n
  Equation format: a_n = c_0*a_(n-k) + c_1*a_(n-(k+1)) + ... + c_(n-1)*a_0\n"
  (let [a_n (if (empty? (first coefficients))
       	    (next-term terms k (take (count terms) (repeat 1)))
	    (next-term terms k (take (count terms) (first coefficients))))]
    (if (empty? coefficients)
      (lazy-seq (cons (first terms) (lrr (shift-terms terms a_n) k)))
      (lazy-seq (cons (first terms) (lrr (shift-terms terms a_n) k (first coefficients)))))))

(defn skolemization [sequence]
  "Finds the index of the zeroes in the linear recurrence relation"
  (loop [result [] it 0]
    (if (>= it (count sequence)) result
      (if (zero? (nth sequence it))
        (recur (conj result it) (inc it))
	(recur result (inc it))))))

(defn -main [& args]
  (print "The Skolem Problem Program\n\n")
  (print "Equation format: a_n = c_0*a_(n-k) + c_1*a_(n-(k+1)) + ... + c_(n-1)*a_0\n\n")
  (let [params (atom []) running (atom true)]
    (while @running
      (print "Enter initial terms: ")(flush)
      (let [input (read-line)]
        (swap! params conj
	  (->> (clojure.string/replace input "," " ")
	       (re-seq #"\S+")
	       (map #(read-string %))
	       vec)))
      (print "Enter k: ")(flush)
      (let [input (read-line)] (swap! params conj (read-string input)))
      (print "Are all coefficients 1? (y/n): ")(flush)
      (let [input (.toUpperCase (read-line))]
        (case input
	  "Y" (swap! params conj '())
	  "N"
	  (do (print "Enter coefficients in order: ") (flush)
	  (let [otherinput (read-line)]
	    (swap! params conj
	      (->> (clojure.string/replace otherinput "," " ")
	      	   (re-seq #"\S+")
		   (map #(read-string %))))))
          (reset! running false)))
      (print "How many terms would you like? ")(flush)
      (let [input (read-string (read-line))
      	    sequence (take input (lrr (get @params 0) (get @params 1) (get @params 2)))
	    skolem-seq (skolemization sequence)]
        (newline)
	(print "Sequence: ")
	(doseq [i sequence] (if (not= i (last sequence)) (print (str i ", ")) (print i)))
	(newline)
	(print "Zeroes: ")
	(doseq [i skolem-seq] (if (not= i (last skolem-seq))
	(print (str "a_" i ", ")) (print (str "a_" i))))
	(newline)(newline))
      (print "You wanna run it again? (y/n): ")(flush)
      (let [input (.toUpperCase (read-line))]
        (case input
	  "Y" (do (reset! params []) (newline))
	  "N" (reset! running false)
	  (reset! running false))))))