(ns sicp.chapter1.exercise-31)

(defn product-recursive [f a next-t b]
  (if (> a b)
    1
    (* (f a)
       (product-recursive f (next-t a) next-t b))))

(defn product [f a next-t b]
  (loop [i      a
         result 1]
    (if (> i b)
      result
      (recur (next-t i)
             (* result (f i))))))

(defn factorial [n]
  (product identity 1 inc n))

(assert (= (factorial 4) 24))

;; PI approximation

(defn appr-pi [n]
  (let [term (fn [i]
               (if (odd? i)
                 (/ (+ 1 i) (+ 2 i))
                 (/ (+ 2 i) (+ 1 i))))]
    (* 4 (double (product term 1 inc n)))))

(appr-pi 100)  ;; => 3.15...
