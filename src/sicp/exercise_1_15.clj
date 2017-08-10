(ns sicp.exercise-1-15
  (:require [clojure.math.numeric-tower :refer [abs]]))

(defn cube [x] (* x x x))

(defn p [x] (- (* 3 x)
               (* 4 (cube x))))

(defn sine [angle]
  (if (<= (abs angle) 0.1)
    angle
    (p (sine (/ angle 3.0)))))

(defn sine-explained [angle]
  (if (<= (abs angle) 0.1)
    angle
    (let [reduc (sine-explained (/ angle 3.0))]
      `(p ~reduc))))

(sine-explained 12.15)  ;; 5 applications of 'p
