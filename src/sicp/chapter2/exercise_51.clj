(ns sicp.chapter2.exercise-51
  (:require [sicp.chapter2.exercise-50 :as e50]
            [sicp.woodcut.drawing :refer [draw]]
            [sicp.woodcut.painters :refer [george]]
            [sicp.woodcut.vecs :as vecs]))

(defn below [p1 p2]
  (let [down (e50/transform-painter p1
                                    (vecs/make-vector 0 0)
                                    (vecs/make-vector 1 0)
                                    (vecs/make-vector 0 0.5))
        up   (e50/transform-painter p2
                                    (vecs/make-vector 0 0.5)
                                    (vecs/make-vector 1 0.5)
                                    (vecs/make-vector 0 1))]
    (fn [frame]
      (down frame)
      (up frame))))

(defn above [p1 p2]
  (below p2 p1))

(defn beside [p1 p2]
  (let [split-point (vecs/make-vector 0.5 0)
        left        (e50/transform-painter p1
                                           (vecs/make-vector 0 0)
                                           split-point
                                           (vecs/make-vector 0 1))
        right       (e50/transform-painter p2
                                           split-point
                                           (vecs/make-vector 1 0)
                                           (vecs/make-vector 0.5 1))]
    (fn [frame]
      (left frame) (right frame))))

(defn below-2 [p1 p2]
  (e50/rotate-270 (beside (e50/rotate-90 p1)
                          (e50/rotate-90 p2))))

(defn quartet [tl tr bl br]
  (below (beside bl br)
         (beside tl tr)))

(defn below-compare [p1 p2]
  (beside (below p1 p2)
          (below-2 p1 p2)))

;; (draw (below-compare george (e50/flip-vert george)))
;; (draw (quartet george
;;                (e50/flip-horiz george)
;;                (e50/flip-vert george)
;;                (e50/squash-inwards george)))
