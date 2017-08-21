(ns sicp.chapter2.exercise-32
  (:require [sicp.chapter2.exercise-18 :as e18]))

(defn subsets [s]
  (if (and (list? s) (empty? s))
    '(())
    (let [rst (subsets (rest s))]
      (e18/append rst (map (fn [l] (cons (first s) l)) rst)))))

(assert (= (subsets '(1 2 3))
           '(() (3) (2) (2 3) (1) (1 3) (1 2) (1 2 3))))
