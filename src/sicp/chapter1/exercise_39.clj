(ns sicp.chapter1.exercise-39
  (:require [sicp.chapter1.exercise-37 :as e37]))

(defn tan-cf [x k]
  {:pre [(pos? k)]}
  (let [n #(if (= % 1) x (- (* x x)))
        d #(- (* % 2) 1)]
    (e37/cont-frac n d k)))

(tan-cf Math/PI 10000)
