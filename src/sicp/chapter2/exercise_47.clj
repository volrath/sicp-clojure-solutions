(ns sicp.chapter2.exercise-47
  (:require [sicp.woodcut.vecs :as vecs]))

(defn make-frame-1 [origin edge1 edge2]
  (list origin edge1 edge2))

(defn origin-frame-1 [frame]
  (first frame))

(defn edge1-frame-1 [frame]
  (second frame))

(defn edge2-frame-1 [frame]
  (nth frame 2))

;; Due to clojure's behavior, these two are basically the same, so I'm just
;; gonna solve them differently.

(defn make-frame-2 [origin edge1 edge2]
  (cons origin (cons edge1 (cons edge2 '()))))

(defn origin-frame-2 [frame]
  (first frame))

(defn edge1-frame-2 [frame]
  (first (rest frame)))

(defn edge2-frame-2 [frame]
  (first (rest (rest frame))))

(let [origin (vecs/make-vector 0 0)
      edge1  (vecs/make-vector 3 0)
      edge2  (vecs/make-vector 0 5)
      frame1 (make-frame-1 origin edge1 edge2)
      frame2 (make-frame-2 origin edge1 edge2)]
  (assert (= (origin-frame-1 frame1) (origin-frame-2 frame2) origin))
  (assert (= (edge1-frame-1 frame1) (edge1-frame-2 frame2) edge1))
  (assert (= (edge2-frame-1 frame1) (edge2-frame-2 frame2) edge2)))
