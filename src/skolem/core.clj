(ns skolem.core
  (:gen-class))

(defn next-term [terms k coefficients]
  (apply +' (map (fn [x] (eval (conj x '*')))
  (partition 2 (interleave (nthrest (reverse terms) (dec k)) coefficients)))))

(defn shift-terms [terms num] (conj (vec (rest terms)) num))

(defn error [] (println "Program exited due to error in input"))

(defn lrr [terms k & coefficients]
  "Generates a linear recurrence relation using initial terms, k, and optional coefficients.\n
  Equation format: a_n = c_0*a_(n-k) + c_1*a_(n-(k+1)) + ... + c_(n-1)*a_0\n"
  (let [a_n (if (empty? coefficients)
       	    (next-term terms k (take (count terms) (repeat 1)))
	    (next-term terms k (take (count terms) (first coefficients))))]
    (if (empty? coefficients)
      (lazy-seq (cons (first terms) (lrr (shift-terms terms a_n) k)))
      (lazy-seq (cons (first terms) (lrr (shift-terms terms a_n) k (first coefficients)))))))

(defn skolemization [sequence]
  "Finds the index of the zero in the linear recurrence relation"
  (loop [result [] it 0]
    (if (>= it (count sequence)) result
      (if (zero? (nth sequence it))
        (recur (conj result it) (inc it))
	(recur result (inc it))))))

(defn -main [& args]
  (print "The Skolem Problem Program\n\n")
  (print "Equation format: a_n = c_0*a_(n-k) + c_1*a_(n-(k+1)) + ... + c_(n-1)*a_0\n\n")
  (let [attr (atom {:terms [] :k 0 :coefficients '()})]
    (print "Enter initial values: ") (flush)
    (swap! attr assoc :terms (vec (map #(read-string %) (re-seq #"\S+" (clojure.string/replace (read-line) "," "")))))
    (print "Enter k: ") (flush)
    (swap! attr assoc :k (read-string (read-line)))
    (print "Are all coefficients 1? (y/n): ") (flush)
    (cond
      (or (= (read-line) "y") (= (read-line) "Y"))
      (do (print "How many terms would you like? ") (flush)
      (let [number (read-string (read-line))]
        (print "\nSequence: ") (doseq [i (take number (lrr (:terms @attr) (:k @attr)))] (print i)) (print "\n")
	(print "Zeroes: ") (doseq [i (skolemization (take number (lrr (:terms @attr) (:k @attr))))] (print i " ")) (print "\n\n"))
      (print "Would you like to redo? ") (flush)
      (if (or (= (read-line) "y") (= (read-line) "Y")) (do (print "\n\n\n") (-main))
      (print "\n"))) 
      (or (= (read-line) "n") (= (read-line) "N"))
      (do (print "Write out coefficients in order: ") (*flush-on-newline*)
      (swap! attr assoc :coefficients (map #(read-string %) (re-seq #"\S+" (clojure.string/replace (read-line) "," ""))))
      (print "How many terms would you like? ") (flush)
      (let [number (read-string (read-line))]
        (print "\nSequence: ") (doseq [i (take number (lrr (:terms @attr) (:k @attr) (:coefficients @attr)))] (print i))
	(print "\nZeroes: ") (doseq [i (skolemization (take number (lrr (:terms @attr) (:k @attr) (:coefficients @attr))))] (print i " ")) (print "\n\n"))
      (print "Would you like to redo? ") (flush)
      (if (or (= (read-line) "y") (= (read-line) "Y")) (do (print "\n\n\n") (-main))
      (print "\n"))))))