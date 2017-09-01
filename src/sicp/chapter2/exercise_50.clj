(ns sicp.chapter2.exercise-50
  (:require [sicp.woodcut.drawing :refer [draw]]
            [sicp.woodcut.painters :refer [george]]
            [sicp.woodcut.vecs :as vecs]))

(defn transform-painter [painter origin corner1 corner2]
  (fn [frame]
    (let [m (vecs/frame-coord-map frame)
          new-origin (m origin)]
      (painter (vecs/make-frame new-origin
                                (vecs/sub-vect (m corner1) new-origin)
                                (vecs/sub-vect (m corner2) new-origin))))))

(defn flip-vert [painter]
  (transform-painter painter
                     (vecs/make-vector 0 1)
                     (vecs/make-vector 1 1)
                     (vecs/make-vector 0 0)))

(defn flip-horiz [painter]
  (transform-painter painter
                     (vecs/make-vector 1 0)
                     (vecs/make-vector 0 0)
                     (vecs/make-vector 1 1)))

(defn shrink-to-upper-right [painter]
  (transform-painter painter
                     (vecs/make-vector 0.5 0.5)
                     (vecs/make-vector 1 0.5)
                     (vecs/make-vector 0.5 1)))

(defn squash-inwards [painter]
  (transform-painter painter
                     (vecs/make-vector 0 0)
                     (vecs/make-vector 0.65 0.35)
                     (vecs/make-vector 0.35 0.65)))

(defn rotate-90 [painter]
  (transform-painter painter
                     (vecs/make-vector 0 1)
                     (vecs/make-vector 0 0)
                     (vecs/make-vector 1 1)))

(defn rotate-180 [painter]
  (transform-painter painter
                     (vecs/make-vector 1 1)
                     (vecs/make-vector 0 1)
                     (vecs/make-vector 1 0)))

(defn rotate-270 [painter]
  (transform-painter painter
                     (vecs/make-vector 1 0)
                     (vecs/make-vector 1 1)
                     (vecs/make-vector 0 0)))
