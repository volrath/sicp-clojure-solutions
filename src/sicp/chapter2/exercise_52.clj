(ns sicp.chapter2.exercise-52
  (:require [sicp.chapter2.exercise-45 :as e45]
            [sicp.chapter2.exercise-50 :as e50]
            [sicp.chapter2.exercise-51 :as e51]
            [sicp.woodcut.drawing :refer [draw]]
            [sicp.woodcut.painters :refer [jimi make-segment segments->painter wave wave-segment-list]]
            [sicp.woodcut.vecs :as vecs]))

(defn corner-split [painter n]
  (if (zero? n)
    painter
    (let [up           (e45/up-split painter (dec n))
          right        (e45/right-split painter (dec n))
          top-left     (e51/beside up up)
          bottom-right (e51/below right right)
          corner       (corner-split painter (dec n))]
      (e51/beside (e51/below painter top-left)
                  (e51/below bottom-right corner)))))

(defn square-of-four [tl tr bl br]
  (fn [painter]
    (let [top    (e51/beside (tl painter) (tr painter))
          bottom (e51/beside (bl painter) (br painter))]
      (e51/below bottom top))))

(def flipped-pairs (square-of-four identity e50/flip-vert identity e50/flip-vert))

(defn square-limit [painter n]
  (let [combine4 (square-of-four e50/flip-horiz identity
                                 e50/rotate-180 e50/flip-vert)]
    (combine4 (corner-split painter n))))

;; (draw (square-limit wave 2))

;; a.

(def smiley-wave (segments->painter
                  (concat wave-segment-list
                          (list
                           (make-segment (vecs/make-vector 0.4 0.78) (vecs/make-vector 0.45 0.7))
                           (make-segment (vecs/make-vector 0.45 0.7) (vecs/make-vector 0.55 0.7))
                           (make-segment (vecs/make-vector 0.55 0.7) (vecs/make-vector 0.6 0.78))))))
;; (draw (square-limit smiley-wave 4))

;; b.

(defn corner-split-2 [painter n]
  (if (zero? n)
    painter
    (let [up           (e45/up-split painter (dec n))
          right        (e45/right-split painter (dec n))
          corner       (corner-split painter (dec n))]
      (e51/beside (e51/below painter up)
                  (e51/below right corner)))))
;; (draw (corner-split-2 smiley-wave 4))

;; c.

(defn square-limit-2 [painter n]
  (let [combine4 (square-of-four e50/flip-horiz identity
                                 e50/rotate-180 e50/flip-vert)]
    (combine4 (e50/flip-vert (e50/flip-horiz (corner-split painter n))))))
;; (draw (square-limit-2 jimi 3))
