(ns sicp.chapter2.exercise-60
  (:require [sicp.chapter2.exercise-33 :as e33]))

;; We are representing sets as list that allow duplicates.

;; best case scenario, same performance, but O(N) anyways
(defn element-of-set? [x s]
  (cond (empty? s) false
        (= x (first s)) true
        :else (element-of-set? x (rest s))))

;; O(1)
(defn adjoin-set [x set]
  (cons x set))

;; Slightly better performance
(defn union-set [s1 s2]
  (e33/append s1 s2))

;; Worse performance because of `element-of-set?`
(defn intersection-set [s1 s2]
  (cond
    (or (empty? s1) (empty? s2))    '()
    (element-of-set? (first s1) s2) (cons (first s1) (intersection-set (rest s1) s2))
    :else                           (intersection-set (rest s1) s2)))
