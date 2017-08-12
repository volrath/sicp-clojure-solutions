(ns sicp.chapter1.exercise-38
  (:require [clojure.math.numeric-tower :refer [abs]]
            [sicp.chapter1.exercise-37 :refer [cont-frac]]))

(defn e-2-approx-d [i]
  (if (< i 2)
    i
    (let [j (- i 2)]
      (if (zero? (rem j 3))
        (- i (/ j 3))
        1))))

(assert (= (map e-2-approx-d (range 1 18))
           '(1 2 1 1 4 1 1 6 1 1 8 1 1 10 1 1 12)))

(defn e-2-approx [k]
  (cont-frac (fn [i] 1)
             e-2-approx-d
             k))

(def e 2.71828182846)
(def tolerance 0.0001)

(def answer
  (loop [j     1
         steps []]
    (let [approx (e-2-approx j)]
      (if (< (abs (- (- e 2) approx)) tolerance)
        {:result approx
         :steps (conj steps {:x j :y approx})}
        (recur (inc j)
               (conj steps {:x j :y approx}))))))

(:result answer)
(:steps  answer)
