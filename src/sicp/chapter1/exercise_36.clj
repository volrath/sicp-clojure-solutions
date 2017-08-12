(ns sicp.chapter1.exercise-36
  (:require [clojure.math.numeric-tower :refer [abs]]))

(defn average-damp [f]
  (let [avg (fn [a b] (/ (+ a b) 2))]
    (fn [x] (avg x (f x)))))

(defn fixed-point-explained [f first-guess]
  (let [tolerance     0.00001
        close-enough? (fn [a b] (< (abs (- a b)) tolerance))]
    (loop [guess first-guess
           step-count 0
           steps [{:x step-count :y first-guess}]]
      (let [nxt (f guess)]
        (if (close-enough? guess nxt)
          {:result nxt :steps (conj steps {:x (inc step-count) :y nxt})}
          (recur nxt
                 (inc step-count)
                 (conj steps {:x (inc step-count) :y nxt})))))))

(:steps (fixed-point-explained (fn [x] (/ (Math/log 1000) (Math/log x))) 2))
(:steps (fixed-point-explained (average-damp (fn [x] (/ (Math/log 1000) (Math/log x)))) 2))

(let [without (fixed-point-explained (fn [x] (/ (Math/log 1000) (Math/log x))) 2)
      with    (fixed-point-explained (average-damp (fn [x] (/ (Math/log 1000) (Math/log x)))) 2)]
  {:result-diff (- (:result without) (:result with))
   :steps-diff  (- (count (:steps without)) (count (:steps with)))})
