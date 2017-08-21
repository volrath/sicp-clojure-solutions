(ns sicp.chapter2.exercise-08
  (:require [sicp.chapter2.exercise-07 :as e07]))

(defn sub-interval [i1 i2]
  (e07/make-interval (- (e07/lower-bound i1) (e07/lower-bound i2))
                     (- (e07/upper-bound i1) (e07/upper-bound i2))))
