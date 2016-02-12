(ns skolem.core
  (:gen-class))

(defn next-term [terms k coefficients]
  (apply + (map (fn [x] (eval (conj x '*)))
  (partition 2 (interleave (nthrest terms (dec k)) coefficients)))))

(defn shift-terms [terms num] (conj (vec (rest terms)) num))

(defn lrr [terms k & coefficients]
  "Generates a linear recurrence relation using initial terms, k, and optional coefficients"
  (let [a_n (if (empty? coefficients)
       	    (next-term terms k (take (count terms) (repeat 1)))
	    (next-term terms k (take (count terms) (first coefficients))))]
    (if (empty? coefficients)
      (lazy-seq (cons (first terms) (lrr (shift-terms terms a_n) k)))
      (lazy-seq (cons (first terms) (lrr (shift-terms terms a_n) k (first coefficients)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
