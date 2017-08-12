(ns sicp.chapter1.exercise-45
  (:require [clojure.math.numeric-tower :refer [expt floor sqrt]]
            [sicp.chapter1.exercise-36 :as e36]
            [sicp.chapter1.exercise-40 :as e40]
            [sicp.chapter1.exercise-43 :as e43]))

(defn nth-root [x n]
  (let [damp-ratio (int (floor (/ (Math/log n) (Math/log 2))))]
    (e40/fixed-point-of-transform (fn [y] (/ x (expt y (- n 1))))
                                  (e43/repeated e36/average-damp damp-ratio)
                                  1.0)))

(nth-root 81 3)
