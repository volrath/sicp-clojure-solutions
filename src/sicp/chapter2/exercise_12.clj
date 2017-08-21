(ns sicp.chapter2.exercise-12
  (:require [sicp.chapter2.exercise-07 :as e07]
            [sicp.chapter2.exercise-09 :as e09]))

(defn make-center-percent [center percent]
  (let [width (/ (* center percent) 100.0)]
    (e07/make-interval (- center width)
                       (+ center width))))

(defn center [i]
  (/ (+ (e07/lower-bound i) (e07/upper-bound i)) 2.0))

(defn percent [i]
  (double (/ (* (e09/width i) 100)
             (center i))))
