(ns sicp.chapter2.exercise-35
  (:require [sicp.chapter2.exercise-33 :as e33]))

(defn count-leaves [tree]
  (e33/accumulate + 0 (map (fn [subtree]
                             (if (list? subtree)
                               (count-leaves subtree)
                               1))
                           tree)))

(let [x '((1 2) (3 4))]
  (assert (and (= (count-leaves x) 4)
               (= (count-leaves (list x x)) 8))))
