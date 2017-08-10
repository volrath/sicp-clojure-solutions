(ns sicp.visualizer
  (:require [thi.ng.color.core :as col]
            [thi.ng.geom.svg.core :as svg]
            [thi.ng.geom.viz.core :as viz]))

(defn export-viz
  [viz path] (->> viz
                  (svg/svg {:width 600 :height 320})
                  (svg/serialize)
                  (spit path)))

(defn color-dist [num-colors]
  (map #(col/as-css (col/as-int24 (col/css (str "hsla(" (+ 200 (* (/ 360 num-colors) (inc %))) ",100%,50%,1.0)"))))
       (range num-colors)))

(defn values-from-points [points]
  (into [] (map (fn [p] [(:x p) (:y p)]) points)))

(defn bar-spec [points color]
  (let [bar-px    (int (/ 530 (count points)))
        bar-width (if (zero? bar-px) 1 bar-px)]
    {:values     (values-from-points points)
     :attribs    {:stroke       color
                  :stroke-width (str bar-width "px")}
     :layout     viz/svg-bar-plot
     :interleave 1
     :bar-width  bar-width
     :offset     0}))

(defn scatter-spec [points color]
  {:values  (values-from-points points)
   :attribs {:fill color :stroke "none"}
   :layout  viz/svg-scatter-plot})

(defn line-spec [points color]
  {:values  (values-from-points points)
   :attribs {:fill "none" :stroke color}
   :layout  viz/svg-line-plot})

(defn area-spec [points color]
  {:values  (values-from-points points)
   :attribs {:fill color :stroke color}
   :layout  viz/svg-area-plot})

(defn data-spec [fns-data]
  (let [flatten-ins  (flatten (map (fn [data] (map #(:x %) (:points data))) fns-data))
        num-fns      (count fns-data)
        plot-spec    (fn [data color]
                       ((or (:layout-fn data) bar-spec) (:points data) color))]
    (map plot-spec
         fns-data
         (color-dist num-fns))))

(defn graph-points [& fns-data]
  (let [flatten-ins  (flatten (map (fn [data] (map #(:x %) (:points data))) fns-data))
        input-min    (apply min flatten-ins)
        input-max    (apply max flatten-ins)
        input-range  (- input-max input-min)
        flatten-outs (flatten (map (fn [data] (map #(:y %) (:points data))) fns-data))
        output-min   (apply min flatten-outs)
        output-max   (apply max flatten-outs)
        output-range (- output-max output-min)]
    (-> {:x-axis (viz/linear-axis
                  {:domain [(- input-min (* input-range 0.06))
                            (+ input-max (* input-range 0.06))]
                   :range  [50 580]
                   :major  (* input-range 0.12)
                   :pos    280
                   :label  (viz/default-svg-label int)})
         :y-axis (viz/linear-axis
                  {:domain      [(- output-min (* output-range 0.06))
                                 (+ output-max (* output-range 0.06))]
                   :range       [280 20]
                   :major       (* output-range 0.1)
                   :pos         50
                   :label-dist  15
                   :label-style {:text-anchor "end"}})
         :grid   {:minor-y true}
         :data   (data-spec fns-data)}
        (viz/svg-plot2d-cartesian)
        (export-viz "viz.svg"))))

(defn- points-out-of-fns [input fns]
  (map (fn [f input]
         {:points (map (fn [i] {:x i :y (f i)}) input)})
       fns
       (repeat input)))

(defn graph-fns [input & fns]
  (apply graph-points (points-out-of-fns input fns)))

(defn my-sin [x]
  (Math/sin x))

(defn my-cos [x]
  (Math/cos x))

(map (fn [x] {:x x :y (my-sin x)}) (range (- Math/PI) Math/PI 0.05))  ;; C-c C-v p [b s l a]

;; two graphs
;; (graph-fns (range (- Math/PI) Math/PI 0.05) my-sin my-cos)
