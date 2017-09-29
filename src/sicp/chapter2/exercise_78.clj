(ns sicp.chapter2.exercise-78
  (:require [sicp.chapter2.exercise-77 :as e77]))


(defn tagged? [x]
  (or (number? x)
      (e77/tagged? x)))

(defn type-tag [x]
  {:pre  [(tagged? x)]
   :post [(symbol? %)]}
  (if (number? x)
    'scheme-number
    (first x)))

(defn contents [x]
  {:pre [(tagged? x)]}
  (if (number? x)
    x
    (second x)))

(defn attach-tag [tag x]
  (if (= tag 'scheme-number)
    x
    (e77/attach-tag tag x)))
