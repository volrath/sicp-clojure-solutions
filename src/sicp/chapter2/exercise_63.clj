(ns sicp.chapter2.exercise-63
  (:require [rhizome.viz :refer [view-tree]]
            [sicp.chapter2.exercise-33 :as e33]))

(defn make-tree [entry left right]
  (let [l (if (empty? left) nil left)
        r (if (empty? right) nil right)]
    (list entry l r)))

(defn entry [tree]
  (first tree))

(defn left-branch [tree]
  (second tree))

(defn right-branch [tree]
  (nth tree 2))

(defn tree->list-1 [tree]
  (if (nil? tree)
    '()
    (e33/append (tree->list-1 (left-branch tree))
                (cons (entry tree) (tree->list-1 (right-branch tree))))))

(defn tree->list-2 [tree]
  (let [copy-to-list (fn copy-to-list [tree result-list]
                       (if (nil? tree)
                         result-list
                         (copy-to-list (left-branch tree)
                                       (cons (entry tree)
                                             (copy-to-list (right-branch tree) result-list)))))]
    (copy-to-list tree '())))

(defn branch? [tree]
  (not (and (nil? (left-branch tree))
            (nil? (right-branch tree)))))

(defn children [tree]
  (filter (comp not nil?) (list (left-branch tree) (right-branch tree))))

(defn view [tree]
  (view-tree branch? children tree
             :node->descriptor (fn [n] {:label (entry n)})))

;; a. Both produce depth-first search, in-order traversals
(let [tree1 (make-tree 7
                       (make-tree 3
                                  (make-tree 1 nil nil)
                                  (make-tree 5 nil nil))
                       (make-tree 9
                                  nil
                                  (make-tree 11 nil nil)))
      tree2 (make-tree 3
                       (make-tree 1 nil nil)
                       (make-tree 7
                                  (make-tree 5 nil nil)
                                  (make-tree 9
                                             nil
                                             (make-tree 11 nil nil))))
      tree3 (make-tree 5
                       (make-tree 3
                                  (make-tree 1 nil nil)
                                  nil)
                       (make-tree 9
                                  (make-tree 7 nil nil)
                                  (make-tree 11 nil nil)))
      traverse-trees (fn [traversal-fn]
                       {:tree1 (traversal-fn tree1)
                        :tree2 (traversal-fn tree2)
                        :tree3 (traversal-fn tree3)})]
  {:tree->list-1 (traverse-trees tree->list-1)
   :tree->list-2 (traverse-trees tree->list-2)})

;; b. `tree->list-2` has better performance since it uses cons instead of append
;; which is O(N). Plus it is iterative recursion.
