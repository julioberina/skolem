(ns skolem.core
  (:gen-class))

(defn lrr
  "Generates a linear recurrence relation associated with details of the equation a[n]"
  ;; If all coefficients are 1
  ([seqlen initnums param_k]
    {:pre [(and
    	    (vector? initnums)
	    (every? integer? initnums)
	    (>= seqlen (count initnums))
	    (> param_k 0))]}
    (let [n (atom (count initnums))
    	  k (atom param_k)
	  value (atom 0)
    	  result (atom initnums)]
      (while (< @n seqlen)
        (dotimes [i (- (count initnums) (dec param_k))]
          (swap! value + (get @result (- @n @k)))
	  (swap! k inc))
        (swap! result conj @value)
	(swap! n inc)
	(reset! k param_k) (reset! value 0))
	(seq @result)))
	
  ;; If some have different coefficients
  ([seqlen initnums param_k coefficients]
    {:pre [(and
           (vector? initnums)
	   (every? integer? initnums)
	   (>= seqlen (count initnums))
	   (> param_k 0))]}
    (let [n (atom (count initnums))
    	  k (atom param_k)
	  value (atom 0)
	  result (atom initnums)]
      (while (< @n seqlen)
        (dotimes [i (- (count initnums) (dec param_k))]
	  (swap! value + (* (get @result (- @n @k)) (get coefficients i)))
	  (swap! k inc))
	(swap! result conj @value)
	(swap! n inc)
	(reset! k param_k) (reset! value 0))
	(seq @result))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
