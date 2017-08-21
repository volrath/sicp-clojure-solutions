(ns sicp.chapter2.exercise-28
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [sicp.chapter2.exercise-18 :as e18]))

(defn fringe [tree]
  (cond
    (not (list? tree)) (list tree)
    (empty? tree) '()
    :else (e18/append (fringe (first tree)) (fringe (rest tree)))))

(let [x '((1 2) (3 4))]
  (assert (= (fringe x) '(1 2 3 4)))
  (assert (= (fringe (list x x)) '(1 2 3 4 1 2 3 4))))

;; spec

;; first a tree-seq? leaves procedure
(defn leaves [tree]
  (filter number? (tree-seq seq? identity tree)))

;; (s/def ::tree (s/or :leaf   number?
;;                     :branch (s/coll-of ::tree)))

;; (defn test-leaves [val]
;;   (= (:ret val) (->> val :args :t (s/unform ::tree) leaves)))

;; (s/fdef fringe
;;         :args (s/cat :t ::tree)
;;         :ret  (s/coll-of number?)
;;         :fn   test-leaves)

;; (stest/abbrev-result (first (stest/check `fringe)))
