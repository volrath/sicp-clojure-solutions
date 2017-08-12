(ns sicp.chapter1.exercise-19
  (:require [clojure.math.numeric-tower :refer [expt]]))

;; p <- p^2 + q^2
;; q <- 2pq + q^2

(defn fib-iter [a b p q c]
  (cond (zero? c) b
        (even? c) (fib-iter a
                            b
                            (+ (expt p 2) (expt q 2))
                            (+ (* 2 p q) (expt q 2))
                            (/ c 2))
        :else     (fib-iter (+ (* b q) (* a q) (* a p))
                            (+ (* b p) (* a q))
                            p
                            q
                            (dec c))))

(defn fib [n]
  (fib-iter 1 0 0 1 n))

(fib 15)
