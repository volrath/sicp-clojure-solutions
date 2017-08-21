(ns sicp.chapter2.exercise-30
  (:require [sicp.chapter2.exercise-21 :as e21]))

(defn square-tree [tree]
  (cond (number? tree) (e21/square tree)
        :else          (cons (square-tree (first tree)) (map square-tree (rest tree)))))

(let [t '(1 (2 (3 4) 5) (6 7))]
  (assert (= (square-tree t) '(1 (4 (9 16) 25) (36 49)))))
