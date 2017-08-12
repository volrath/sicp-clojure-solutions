(ns sicp.chapter2.exercise-03
  (:require [sicp.chapter2.exercise-02 :as e02]))

;; two representations:
;; 1. with points, using top-left point and bottom-right point
;; 2. with size, using with, height and top-left corner
;; -----

;; 1.
(defn make-rect-1 [top-left bottom-right]
  (vector top-left bottom-right))

(defn width-1 [rect]
  (let [top-left     (first rect)
        bottom-right (second rect)]
    (- (e02/x-point bottom-right) (e02/x-point top-left))))

(defn height-1 [rect]
  (let [top-left     (first rect)
        bottom-right (second rect)]
    (- (e02/y-point top-left) (e02/y-point bottom-right))))

;; 2.
(defn make-rect-2 [top-left w h]
  (vector top-left w h))

(defn width-2 [rect]
  (second rect))

(defn height-2 [rect]
  (nth rect 2))

;; end abstraction

(defn abstract-select [rect selector abstraction]
  (let [fn-name (str selector "-" (name abstraction))]
    ((resolve (symbol fn-name)) rect)))

(defn width [rect abstraction]
  (abstract-select rect "width" abstraction))

(defn height [rect abstraction]
  (abstract-select rect "height" abstraction))

(defn perimeter
  ([rect] (perimeter rect :1))
  ([rect abstraction]
   (let [w (width rect abstraction)
         h (height rect abstraction)]
     (* 2 (+ w h)))))

(defn area
  ([rect] (area rect :1))
  ([rect abstraction]
   (let [w (width rect abstraction)
         h (height rect abstraction)]
     (* w h))))

(let [rect1 (make-rect-1 (e02/make-point 0 4) (e02/make-point 8 0))
      rect2 (make-rect-2 (e02/make-point -3 0) 3 5)]
  (assert (= (area rect1) 32)
          (= (perimeter rect1) 24))
  (assert (= (area rect2 :2) 15)
          (= (perimeter rect2 :2) 16)))
