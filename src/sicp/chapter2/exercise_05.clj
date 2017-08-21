(ns sicp.chapter2.exercise-05
  (:require [clojure.math.numeric-tower :refer [expt]]))

;; taken from http://community.schemewiki.org/?sicp-ex-2.5

;; Pair a, b can be stored as a single integer 2^a * 3^b, provided a
;; and b are both non-negative integers.  The first part will be even,
;; the last odd.  Getting rid of the even part will leave the odd, and
;; vice versa.

(defn a-cons [a b]
  {:pre [(pos? a) (pos? b)]}
  (* (expt 2 a) (expt 3 b)))

(defn reminder-divisions [n divisor]
  (let [divides? (fn [d n] (zero? (rem n d)))]
    (loop [try-expt 1]
      (if (not (divides? (expt divisor try-expt) n))
        (dec try-expt)
        (recur (inc try-expt))))))

(defn a-car [z]
  (reminder-divisions z 2))

(defn a-cdr [z]
  (reminder-divisions z 3))

(let [pair (a-cons 11 17)]
  (assert (and (= (a-car pair) 11)
               (= (a-cdr pair) 17))))
