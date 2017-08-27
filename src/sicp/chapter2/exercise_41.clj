(ns sicp.chapter2.exercise-41
  (:require [sicp.chapter2.exercise-40 :as e40]))

(defn unique-distinct-tuples [n s]
  (if (= s 1)
    (map list (range 1 (inc n)))
    (e40/flatmap
     (fn [i]
       (map (fn [sub-t] (cons i sub-t))
            (unique-distinct-tuples (dec i) (dec s))))
     (range (dec s) (inc n)))))

(defn triples-sum-equal [n s]
  (let [sum? (fn [tup] (= s (apply + tup)))]
    (filter sum? (unique-distinct-tuples n 3))))

(assert (= (triples-sum-equal 6 12) '((5 4 3)
                                      (6 4 2)
                                      (6 5 1))))
