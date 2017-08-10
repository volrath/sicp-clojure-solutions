(ns sicp.exercise-1-45
  (:require [clojure.math.numeric-tower :refer [expt floor sqrt]]
            [sicp.exercise-1-36 :as e36]
            [sicp.exercise-1-40 :as e40]
            [sicp.exercise-1-43 :as e43]))

(defn nth-root [x n]
  (let [damp-ratio (floor (/ (Math/log n) (Math/log 2)))]
    (e40/fixed-point-of-transform (fn [y] (/ x (expt y (- n 1))))
                                  (e43/repeated e36/average-damp (int (/ n 2)))
                                  1.0)))
