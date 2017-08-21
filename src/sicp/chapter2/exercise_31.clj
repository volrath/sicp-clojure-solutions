(ns sicp.chapter2.exercise-31
  (:require [sicp.chapter2.exercise-21 :as e21]))

(defn tree-map [f tree]
  (if (list? tree)
    (map (fn [subtree]
           (if (list? subtree)
             (tree-map f subtree)
             (f subtree)))
         tree)))

(defn square-tree [tree]
  (tree-map e21/square tree))

(let [t '(1 (2 (3 4) 5) (6 7))]
  (assert (= (square-tree t) '(1 (4 (9 16) 25) (36 49)))))
