(ns sicp.chapter1.exercise-16
  (:require [clojure.math.numeric-tower :refer [expt sqrt]]))

;; secret here, two accumulators, acc for odd n's, b for evens.
(defn iter-expt [acc b n]
  (cond (zero? n) acc
        (even? n) (iter-expt acc (* b b) (/ n 2))
        :else     (iter-expt (* acc b) b (dec n))))

(defn fast-expt-iter [b n]
  (iter-expt 1 b n))

(expt 2 5)
(fast-expt-iter 2 10)
