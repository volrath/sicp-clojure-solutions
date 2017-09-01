(ns sicp.woodcut.vecs
  (:require [clojure.spec.alpha :as s]))

;;; Exercise 2.46

(s/def ::x number?)
(s/def ::y number?)
(s/def ::vector (s/keys :req [::x ::y]))

(defn make-vector [x y]
  {:x x :y y})

(defn xcor-vect [v]
  (:x v))

(defn ycor-vect [v]
  (:y v))

(defn add-vect [v1 v2]
  (make-vector
   (+ (xcor-vect v1) (xcor-vect v2))
   (+ (ycor-vect v1) (ycor-vect v2))))

(defn sub-vect [v1 v2]
  (make-vector
   (- (xcor-vect v1) (xcor-vect v2))
   (- (ycor-vect v1) (ycor-vect v2))))

(defn scale-vect [s v]
  (make-vector
   (* s (xcor-vect v))
   (* s (ycor-vect v))))

;;; Another solution for exercise 2.47 (real answer in sicp/chapter2/exercise_47.clj)

(s/def ::origin ::vector)
(s/def ::edge1 ::vector)
(s/def ::edge2 ::vector)
(s/def ::frame (s/keys :req [::origin ::edge1 ::edge2]))

(defn make-frame [origin edge1 edge2]
  {::origin origin
   ::edge1 edge1
   ::edge2 edge2})

(defn origin-frame [{:keys [::origin]}]
  origin)

(defn edge1-frame [{:keys [::edge1]}]
  edge1)

(defn edge2-frame [{:keys [::edge2]}]
  edge2)

;;; --

(defn frame-coord-map [frame]
  (fn [v]
    (add-vect
     (origin-frame frame)
     (add-vect (scale-vect (xcor-vect v)
                           (edge1-frame frame))
               (scale-vect (ycor-vect v)
                           (edge2-frame frame))))))
