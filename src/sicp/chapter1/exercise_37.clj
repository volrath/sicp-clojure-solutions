(ns sicp.chapter1.exercise-37
  (:require [clojure.math.numeric-tower :refer [abs]]))

;; a.
(defn cont-frac [n d k]
  {:pre [(pos? k)]}
  (loop [i k
         result (double (/ (n k) (d k)))]
    (if (= i 1)
      (/ result (d i))
      (let [prev-i (dec i)]
        (recur prev-i
               (/ (n prev-i) (+ (d prev-i) result)))))))

(def golden-ratio 0.61803398875)
(def tolerance 0.0001)

(def answer
  (loop [j     1 ;; i know k should be > 5
         steps []]
    (let [ratio (cont-frac (fn [i] 1)
                           (fn [i] 1)
                           j)]
      (if (< (abs (- golden-ratio ratio)) tolerance)
        {:result j
         :steps (conj steps {:x j :y ratio})}
        (recur (inc j)
               (conj steps {:x j :y ratio}))))))

(:steps answer)

;; b.

(defn cont-frac-recur [n d k c]
  (if (= c k)
    (double (/ (n k) (d k)))
    (/ (n c) (+ (d c) (cont-frac-recur n d k (inc c))))))

(defn cont-frac-2 [n d k]
  {:pre [(pos? k)]}
  (cont-frac-recur n d k 1))

(cont-frac-2 (fn [i] 1)
             (fn [i] 1)
             10)
