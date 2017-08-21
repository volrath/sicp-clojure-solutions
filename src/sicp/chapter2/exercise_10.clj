(ns sicp.chapter2.exercise-10
  (:require [sicp.chapter2.exercise-07 :as e07]))

(defn div-interval [i1 i2]
  {:pre [(not (zero? (e07/lower-bound i2)))
         (not (zero? (e07/upper-bound i2)))]}
  (e07/div-interval i1 i2))
