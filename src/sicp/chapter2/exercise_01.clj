(ns sicp.chapter2.exercise-01
  (:require [clojure.math.numeric-tower :refer [abs gcd]]))

(defn make-rat [n d]
  (let [norm     #(/ % (abs %))
        nsign    (norm n)
        dsign    (norm d)
        rat-sign (* nsign dsign)
        g        (gcd n d)
        new-n    (/ (abs n) g)
        new-d    (/ (abs d) g)]
    (vector (* rat-sign new-n) new-d)))

(defn numer [rat]
  (first rat))

(defn denom [rat]
  (second rat))

(defn print-rat [rat]
  (str (numer rat) "/" (denom rat)))

(assert (and (= (print-rat (make-rat 4 6))   "2/3")
             (= (print-rat (make-rat -4 -6)) "2/3")
             (= (print-rat (make-rat -4 6)) "-2/3")
             (= (print-rat (make-rat 4 -6)) "-2/3")))
