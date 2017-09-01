(ns sicp.woodcut.painters
  (:require [clojure.spec.alpha :as s]
            [quil.core :as q]
            [sicp.woodcut.vecs :as vecs]))

;;; exercise 2.48

(s/def start ::vecs/vector)
(s/def end ::vecs/vector)
(s/def segment (s/keys :req [::start ::end]))

(defn make-segment [s e]
  {::start s
   ::end e})

(defn start-segment [{:keys [::start]}]
  start)

(defn end-segment [{:keys [::end]}]
  end)

;;; --

(defn draw-line [from-v to-v]
  (let [height (q/height)
        invert-y #(q/map-range % 0 height height 0)]
    (q/line (vecs/xcor-vect from-v)
            (invert-y (vecs/ycor-vect from-v))
            (vecs/xcor-vect to-v)
            (invert-y (vecs/ycor-vect to-v)))))

(defn segments->painter [segment-list]
  (fn [frame]
    (doseq [segment segment-list]
      (draw-line
       ((vecs/frame-coord-map frame) (start-segment segment))
       ((vecs/frame-coord-map frame) (end-segment segment))))))

;; Taken from: http://www.sicpdistilled.com/section/2-escher/
;; Important: Mixin segment-painters with image->painters is currently not
;; working as expected. Particularly y-axis get messed up in image->painters.
(defn image->painter [img-src]
  (fn [{{ox :x oy :y}   ::vecs/origin
        {e1x :x e1y :y} ::vecs/edge1
        {e2x :x e2y :y} ::vecs/edge2}]
    (let [img (q/load-image img-src)
          iw  (.width img)
          ih  (.height img)]
      (q/push-matrix)
      (q/reset-matrix)
      (q/apply-matrix (/ e1x iw) (/ e2x iw) ox
                      (/ e1y ih) (/ e2y ih) oy)
      (q/image img 0 0)
      (q/pop-matrix)
      )))

(def jimi (image->painter "jimi.jpg"))

;;; --

;; Taken from
;; http://community.schemewiki.org/?sicp-ex-2.49

(def wave-segment-list
  (list
   (make-segment (vecs/make-vector 0.25 0) (vecs/make-vector 0.35 0.5))
   (make-segment (vecs/make-vector 0.35 0.5) (vecs/make-vector 0.3 0.6))
   (make-segment (vecs/make-vector 0.3 0.6) (vecs/make-vector 0.15 0.4))
   (make-segment (vecs/make-vector 0.15 0.4) (vecs/make-vector 0 0.65))
   ;; (make-segment (vecs/make-vector 0 0.65) (vecs/make-vector 0 0.85))
   (make-segment (vecs/make-vector 0 0.85) (vecs/make-vector 0.15 0.6))
   (make-segment (vecs/make-vector 0.15 0.6) (vecs/make-vector 0.3 0.65))
   (make-segment (vecs/make-vector 0.3 0.65) (vecs/make-vector 0.4 0.65))
   (make-segment (vecs/make-vector 0.4 0.65) (vecs/make-vector 0.35 0.85))
   (make-segment (vecs/make-vector 0.35 0.85) (vecs/make-vector 0.4 1))
   ;; (make-segment (vecs/make-vector 0.4 1) (vecs/make-vector 0.6 1))
   (make-segment (vecs/make-vector 0.6 1) (vecs/make-vector 0.65 0.85))
   (make-segment (vecs/make-vector 0.65 0.85) (vecs/make-vector 0.6 0.65))
   (make-segment (vecs/make-vector 0.6 0.65) (vecs/make-vector 0.75 0.65))
   (make-segment (vecs/make-vector 0.75 0.65) (vecs/make-vector 1 0.35))
   ;; (make-segment (vecs/make-vector 1 0.35) (vecs/make-vector 1 0.15))
   (make-segment (vecs/make-vector 1 0.15) (vecs/make-vector 0.6 0.45))
   (make-segment (vecs/make-vector 0.6 0.45) (vecs/make-vector 0.75 0))
   ;; (make-segment (vecs/make-vector 0.75 0) (vecs/make-vector 0.6 0))
   (make-segment (vecs/make-vector 0.6 0) (vecs/make-vector 0.5 0.3))
   (make-segment (vecs/make-vector 0.5 0.3) (vecs/make-vector 0.4 0))
   ;; (make-segment (vecs/make-vector 0.4 0) (vecs/make-vector 0.25 0))
   ))

(def wave (segments->painter wave-segment-list))

(def george wave)
