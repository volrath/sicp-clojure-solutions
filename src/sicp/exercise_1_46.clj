(ns sicp.exercise-1-46
  (:require [clojure.math.numeric-tower :refer [abs]]
            [sicp.exercise-1-23 :refer [avg]]))

(defn iterative-improve [good-enough? improve]
  (fn [guess]
    (loop [val guess]
      (if (good-enough? val)
        val
        (recur (improve val))))))

(def tolerance 0.001)

;; a.
(defn sqrt [x]
  (let [square       #(* % %)
        good-enough? (fn [guess]
                       (< (abs (- (square guess) x)) tolerance))
        improve      (fn [guess]
                       (avg [guess (/ x guess)]))]
    ((iterative-improve good-enough? improve) 1.0)))

(sqrt 25)

;; b.
(defn fixed-point [f first-guess]
  (let [good-enough? (fn [guess]
                       (< (abs (- guess (f guess))) tolerance))]
    ((iterative-improve good-enough? f) first-guess)))

(fixed-point #(Math/cos %) 1.0)
