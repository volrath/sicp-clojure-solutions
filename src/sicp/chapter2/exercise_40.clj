(ns sicp.chapter2.exercise-40
  (:require [sicp.chapter1.exercise-23 :as c1e23]
            [sicp.chapter2.exercise-33 :as e33]))

(defn flatmap [f s]
  (e33/accumulate e33/append '() (map f s)))

(defn unique-pairs [n]
  (flatmap
   (fn [i]
     (map (fn [j] (list i j))
          (range 1 i)))
   (range 1 (inc n))))

(defn filter-map [map-fn filter-fn s]
  (map map-fn (filter filter-fn s)))

(defn prime-sum-pairs [n]
  (let [make-pair-sum (fn [[i j]] (list i j (+ i j)))
        prime-sum?    (fn [[i j]] (c1e23/prime? (+ i j)))]
    (map make-pair-sum
         (filter prime-sum? (unique-pairs n)))))

(assert (= (prime-sum-pairs 6) '((2 1 3)
                                 (3 2 5)
                                 (4 1 5)
                                 (4 3 7)
                                 (5 2 7)
                                 (6 1 7)
                                 (6 5 11))))
