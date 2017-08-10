(ns sicp.exercise-1-44
  (:require [sicp.exercise-1-23 :as e23]
            [sicp.exercise-1-43 :as e43]))

(defn smooth [f]
  (let [dx 0.001]
    (fn [x]
      (e23/avg (f (- x dx))
               (f x)
               (f (+ x dx))))))

(defn n-fold-smooth [f n]
  ((e43/repeated smooth n) f))
