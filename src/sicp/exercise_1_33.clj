(ns sicp.exercise-1-33
  (:require [sicp.exercise-1-20 :as e20]
            [sicp.exercise-1-23 :as e23]
            [sicp.exercise-1-31 :as e31]))

(defn filter-accumulate [filter-fn combiner null-value f a next-t b]
  (loop [i      a
         result null-value]
    (if (> i b)
      result
      (let [fi (if (filter-fn i) (f i) null-value)]
        (recur (next-t i)
               (combiner result fi))))))

;; a.
(defn sum-square-primes [a b]
  (let [term #(* % %)]
    (filter-accumulate e23/prime? + 0 term a inc b)))

(sum-square-primes 0 13)  ;; 1 + 4 + 9 + 25 + 49 + 121 + 169 = 378

;; b.
(defn product-relatively-prime [n]
  (let [filter-fn #(= (e20/gcd n %) 1)]
    (filter-accumulate filter-fn * 1 identity 1 inc n)))
