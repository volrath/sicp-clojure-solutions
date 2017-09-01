(ns sicp.chapter2.exercise-49
  (:require [sicp.woodcut.drawing :refer [draw]]
            [sicp.woodcut.painters :refer [make-segment segments->painter wave]]
            [sicp.woodcut.vecs :as vecs]))

;; a.

(def frame-painter
  (segments->painter (list
                      (make-segment (vecs/make-vector 0 0) (vecs/make-vector 1 0))
                      (make-segment (vecs/make-vector 0 0) (vecs/make-vector 0 1))
                      (make-segment (vecs/make-vector 1 1) (vecs/make-vector 1 0))
                      (make-segment (vecs/make-vector 1 1) (vecs/make-vector 0 1)))))

;; (draw frame-painter)

;; b.

(def x-painter
  (segments->painter (list
                      (make-segment (vecs/make-vector 0 0) (vecs/make-vector 1 1))
                      (make-segment (vecs/make-vector 1 0) (vecs/make-vector 0 1)))))

;; (draw x-painter)

;; c.

(def diamond-painter
  (segments->painter (list
                      (make-segment (vecs/make-vector 0.5 0) (vecs/make-vector 0 0.5))
                      (make-segment (vecs/make-vector 0 0.5) (vecs/make-vector 0.5 1))
                      (make-segment (vecs/make-vector 0.5 1) (vecs/make-vector 1 0.5))
                      (make-segment (vecs/make-vector 1 0.5) (vecs/make-vector 0.5 0)))))

;; (draw diamond-painter)

;; d.

;; (draw wave)  ;; wave taken from sicp.woodcut.painters
