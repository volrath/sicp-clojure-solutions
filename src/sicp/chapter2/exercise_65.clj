(ns sicp.chapter2.exercise-65
  (:require [sicp.chapter2.exercise-63 :as e63]
            [sicp.chapter2.exercise-64 :as e64]
            [sicp.chapter2.exercise-62 :as e62]))

(defn union-set [s1 s2]
  (e64/list->tree (e62/union-set (e63/tree->list-2 s1)
                                 (e63/tree->list-2 s2))))

(def intersection-set [s1 s2]
  (e64/list->tree (e62/intersection-set (e63/tree->list-2 s1)
                                        (e63/tree->list-2 s2))))

;; here's a different approximation

(defn element-of-set? [x set]
  (cond (nil? set) false
        (= x (e63/entry set)) true
        (< x (e63/entry set)) (element-of-set? x (e63/left-branch set))
        (> x (e63/entry set)) (element-of-set? x (e63/right-branch set))))

(defn union-set-2 [s1 s2]
  (let [e1 (e63/entry s1)
        e2 (e63/entry s2)
        l1 (e63/left-branch s1)
        l2 (e63/left-branch s2)
        r1 (e63/right-branch s1)
        r2 (e63/right-branch s2)]
    (cond (= e1 e2) (e63/make-tree e1
                                   (union-set l1 l2)
                                   (union-set r1 r2))
          (< e1 e2) (e63/make-tree e2
                                   (union-set s1 l2)
                                   l2)
          (> e1 e2) (e63/make-tree e1
                                   l1
                                   (union-set r1 s2)))))
