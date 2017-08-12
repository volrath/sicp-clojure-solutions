(ns sicp.chapter1.exercise-44
  (:require [sicp.chapter1.exercise-23 :as e23]
            [sicp.chapter1.exercise-43 :as e43]))

(defn smooth [f]
  (let [dx 0.001]
    (fn [x]
      (e23/avg (f (- x dx))
               (f x)
               (f (+ x dx))))))

(defn n-fold-smooth [f n]
  ((e43/repeated smooth n) f))
