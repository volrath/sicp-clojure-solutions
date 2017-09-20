(ns sicp.chapter2.exercise-64
  (:require [rhizome.viz :refer [view-tree]]
            [sicp.chapter2.exercise-63 :as e63]))

(defn partial-tree [elts n]
  (if (zero? n)
    ['() elts]
    (let [left-size      (quot (dec n) 2)
          left-result    (partial-tree elts left-size)
          left-tree      (first left-result)
          non-left-elts  (second left-result)
          this-entry     (first non-left-elts)
          right-size     (- n (inc left-size))
          right-result   (partial-tree (rest non-left-elts) right-size)
          right-tree     (first right-result)
          remaining-elts (second right-result)]
      [(e63/make-tree this-entry left-tree right-tree)
       remaining-elts])))

(defn list->tree [elements]
  (first (partial-tree elements (count elements))))


;; a. It's the regular divide and conquer strategy: it starts by getting
;; the "size" (amount) of elements that should be allocated to the left branch,
;; which in an ordered list is floor((n-1) / 2), and builds the left branch
;; recursively. Every time that it returns, it does so with the "untree-d"
;; elements to the right. From those elements to the right, it takes the first
;; as the root of the (sub)tree and tries to create a tree with the remaining
;; elements as the right tree.
(view-tree e63/branch? e63/children (list->tree '(1 3 5 7 9 11))
           :node->descriptor (fn [n] {:label (first n)}))

;; b. O(n) -> it's basically O (n/2) for each branch.
