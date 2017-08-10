(ns sicp.exercise-1-7
  (:require [clojure.math.numeric-tower :as math]))

(defn abs [x]
  (if (< x 0)
    (- x)
    x))

(defn good-enough-1? [guess x]
  (< (abs (- (* guess guess) x)) 0.001))

(defn good-enough-2? [past current]
  (let [threshold #(/ (* % 5) 100)]
    (< (abs (- current past)) (threshold current))))

(defn average [x y]
  (/ (+ x y) 2))

(defn improve [guess x]
  (average guess (/ x guess)))

(defn sqrt-iter-1 [guess x]
  (if (good-enough-1? guess x)
    guess
    (sqrt-iter-1 (improve guess x) x)))

(defn sqrt-iter-2 [past-guess guess x]
  (if (good-enough-2? past-guess guess)
    guess
    (sqrt-iter-2 guess (improve guess x) x)))

(defn compare-sqrt [x]
  (let [org (math/sqrt x)
        v1 (sqrt-iter-1 1.0 x)
        v2 (sqrt-iter-2 0 1.0 x)]
    {:org org
     :v1 v1
     :v2 v2
     :diff-1 (abs (- org v1))
     :diff-2 (abs (- org v2))}))

(compare-sqrt 0.001)
