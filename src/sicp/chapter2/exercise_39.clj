(ns sicp.chapter2.exercise-39
  (:require [sicp.chapter2.exercise-33 :as e33]
            [sicp.chapter2.exercise-38 :as e38]))

(defn reverse-fr [sequence]
  (e38/fold-right (fn [el acc]
                    (e33/append acc (list el)))
                  '()
                  sequence))

(defn reverse-fl [sequence]
  (e38/fold-left (fn [acc el]
                   (cons el acc))
                 '()
                 sequence))

(let [s '(1 2 3 4)]
  (assert (and (= (reverse-fr s) '(4 3 2 1))
               (= (reverse-fl s) '(4 3 2 1)))))
