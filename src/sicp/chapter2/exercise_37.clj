(ns sicp.chapter2.exercise-37
  (:require [sicp.chapter2.exercise-33 :as e33]
            [sicp.chapter2.exercise-36 :as e36]))

(defn dot-product [v w]
  (e33/accumulate + 0 (map * v w)))

(defn matrix-*-vector [m v]
  (map #(dot-product % v) m))

(defn transpose [m]
  (e36/accumulate-n cons '() m))

(defn matrix-*-matrix [m n]
  (let [cols (transpose n)]
    (map (fn [v] (matrix-*-vector cols v)) m)))
