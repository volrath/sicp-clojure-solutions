(ns sicp.chapter2.exercise-38
  (:require [sicp.chapter2.exercise-33 :as e33]))

(def fold-right e33/accumulate)

(defn fold-left [op initial sequence]
  (loop [result initial
         seq    sequence]
    (if (empty? seq)
      result
      (recur (op result (first seq))
             (rest seq)))))

(assert (= (fold-right / 1 '(1 2 3)) 3/2))
(assert (= (fold-left / 1 '(1 2 3)) 1/6))

(assert (= (fold-right list nil '(1 2 3)) '(1 (2 (3 nil)))))
(assert (= (fold-left list nil '(1 2 3)) '(((nil 1) 2) 3)))
