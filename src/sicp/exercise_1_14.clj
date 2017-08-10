(ns sicp.exercise-1-14
  (:require [clojure.test :as t]))

(def first-denomination {1 1
                         2 5
                         3 10
                         4 25
                         5 50})

(defn cc [amount kinds-of-coin]
  (cond (zero? amount) 1
        (or (< amount 0) (zero? kinds-of-coin)) 0
        :else (+ (cc amount (dec kinds-of-coin))
                 (cc (- amount (get first-denomination kinds-of-coin))
                     kinds-of-coin))))

(defn cc-data [amount kinds-of-coin]
  (let [form `(cc ~amount ~kinds-of-coin)]
    (cond (zero? amount) {:form form :value 1}
          (or (< amount 0) (zero? kinds-of-coin)) {:form form :value 0}
          :else {:form  form
                 :left  (cc-data amount (dec kinds-of-coin))
                 :right (cc-data (- amount (get first-denomination kinds-of-coin))
                                 kinds-of-coin)})))

(defn cc-from-data [tree]
  (if (contains? tree :value)
    (:value tree)
    (+ (cc-from-data (:left tree)) (cc-from-data (:right tree)))))

(assert (= (cc-from-data (cc-data 11 5))
           (cc 11 5)))
