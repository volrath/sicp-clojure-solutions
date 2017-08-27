(ns sicp.chapter2.exercise-43
  (:require [sicp.chapter2.exercise-40 :as e40]
            [sicp.chapter2.exercise-42 :as e42]))

(defn louis-reasoner-queen-cols [k board-size]
  (if (zero? k)
    (list e42/empty-board)
    (filter
     (fn [positions] (e42/safe? k positions))
     (e40/flatmap
      (fn [new-row]
        (map (fn [rest-of-queens]
               (e42/adjoin-position new-row k rest-of-queens))
             (louis-reasoner-queen-cols (dec k) board-size)))  ;; calling *board-size* times `louis-reasoner-queen-cols`
      (range 1 (inc board-size))))))

(defn louis-reasoner-queens [board-size]
  (louis-reasoner-queen-cols board-size board-size))

(louis-reasoner-queens 4)
