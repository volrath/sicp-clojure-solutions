(ns sicp.chapter1.exercise-43
  (:require [sicp.chapter1.exercise-42 :as e42]))

(defn repeated [f n]
  {:pre [(pos? n)]}
  (fn [x]
    (if (= n 1)
      (f x)
      ((e42/compose f (repeated f (dec n))) x))))


(def square #(* % %))

((repeated square 4) 5)

(assert (= ((repeated square 4) 5)
           (square (square (square (square 5))))))
