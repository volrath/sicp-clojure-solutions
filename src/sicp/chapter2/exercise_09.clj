(ns sicp.chapter2.exercise-09
  (:require [sicp.chapter2.exercise-07 :as e07]))

(defn width [i]
  (/ (- (e07/upper-bound i) (e07/lower-bound i))
     2))

;;    (width (sum-interval x y))
;; => (/ (- (upper-bound (sum-interval x y)) (lower-bound (sum-interval x y)))
;;       2)
;; => (/ (- (+ (upper-bound x) (upper-bound y)) (+ (lower-bound x) (lower-bound y)))
;;       2)
;; => (/ (+ (- (upper-bound x) (lower-bound x)) (- (upper-bound y) (lower-bound y)))
;;       2)
;; => (+ (/ (- (upper-bound x) (lower-bound x))
;;          2)
;;       (/ (- (upper-bound y) (lower-bound y))
;;          2))
;; => (+ (width x) (width y))
