(ns sicp.exercise-1-8
  (:require [clojure.math.numeric-tower :as math]
            [clojure.test :as t]))

(defn good-enough? [past current]
  (let [threshold #(/ (* % 5) 100)]
    (< (math/abs (- current past)) (threshold current))))

(defn improve [guess x]
  (/ (+ (/ x (math/expt guess 2)) (* guess 2)) 3))

(defn cube-root-iter [past-guess guess x]
  (if (good-enough? past-guess guess)
    guess
    (cube-root-iter guess (improve guess x) x)))

(defn cube-root [x]
  (cube-root-iter 0 1.0 x))


(math/expt (cube-root 26) 3)
