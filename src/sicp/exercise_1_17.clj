(ns sicp.exercise-1-17
  (:require [clojure.test :as t]))

(defn mul-linear [a b]
  (if (zero? b)
    0
    (+ a (mul-linear a (dec b)))))

(def double #(* % 2))
(def halve #(/ % 2))

(defn mul-log [a b]
  (cond (zero? b) 0
        (even? b) (double (mul-log a (halve b)))
        :else     (+ a (mul-log a (dec b)))))

(mul-linear 2 3)
(mul-log 2 9)
