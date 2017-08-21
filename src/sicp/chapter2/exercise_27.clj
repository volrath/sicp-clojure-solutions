(ns sicp.chapter2.exercise-27
  (:require [sicp.chapter2.exercise-18 :as e18]))

(def x '((1 2) (3 4)))

(defn deep-reverse [tree]
  (if (not (list? tree))
    tree
    (e18/>reverse (map deep-reverse tree))))

(assert (= (e18/>reverse x) '((3 4) (1 2))))
(assert (= (deep-reverse x) '((4 3) (2 1))))
