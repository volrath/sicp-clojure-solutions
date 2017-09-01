(ns sicp.woodcut.drawing
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [sicp.woodcut.vecs :as vecs]))

(def sketch (atom nil))

(defn setup-canvas! [width height]
  ;; (q/scale 1 -1)
  ;; (q/translate 0 (- height))
  (q/no-stroke)
  (q/fill 234)
  (q/rect 0 0 width height)

  (let [padding  (* (min width height) 0.02)
        n-width  (- width (* 2 padding))
        n-height (- height (* 2 padding))]
    (q/fill 255)
    (q/rect padding padding n-width n-height)
    (q/stroke 0)
    (vecs/make-frame (vecs/make-vector padding padding)
                     (vecs/make-vector n-width 0)
                     (vecs/make-vector 0 n-height))))

(defn draw-painter [state]
  (let [{:keys [painter opts]} @sketch
        width                  (first (:size opts))
        height                 (second (:size opts))
        canvas-frame           (setup-canvas! width height)]
    (painter canvas-frame)))

(defn close-sketch [state]
  (reset! sketch nil))

(defn new-sketch [painter opts]
  (let [original-out *out*]
    (reset! sketch
            {:painter painter
             :opts opts
             :quil-skt (q/defsketch woodcut
                         :draw draw-painter
                         :on-close close-sketch
                         :middleware [m/fun-mode]
                         :title (:title opts)
                         :size (:size opts))})))

(defn draw
  ([painter] (draw painter {:title "Woodcut"
                            :size [500 500]}))
  ([painter opts]
   (when (and (not (nil? @sketch))
              (not= (:opts @sketch) opts))
     (q/exit)
     (reset! sketch nil))
   (if (nil? @sketch)
     (new-sketch painter opts)
     (swap! sketch update :painter painter))))
