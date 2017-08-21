(ns sicp.chapter2.exercise-26
  (:require [sicp.chapter2.exercise-18 :as e18]))

(def x (list 1 2 3))
(def y (list 4 5 6))

(assert (= (e18/append x y) '(1 2 3 4 5 6)))
(assert (= (cons x y) '((1 2 3) 4 5 6)))
(assert (= (list x y) '((1 2 3) (4 5 6))))
