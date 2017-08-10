(ns sicp.exercise-1-35
  (:require [clojure.math.numeric-tower :refer [abs]]))

;; x / y = (x + y) / x
;; suppose y = 1
;; x = (x + 1) / x
;; x = 1 + (1 / x)

(defn fixed-point [f first-guess]
  (let [tolerance     0.00001
        close-enough? (fn [a b] (< (abs (- a b)) tolerance))]
    (loop [guess first-guess]
      (let [nxt (f guess)]
        (if (close-enough? guess nxt)
          nxt
          (recur nxt))))))

(double (fixed-point  ;; golden ratio
         (fn [x] (+ 1 (/ 1 x)))
         1))
