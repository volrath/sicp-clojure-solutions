(ns sicp.chapter2.exercise-42
  (:require [clojure.math.numeric-tower :refer [abs]]
            [sicp.chapter2.exercise-40 :as e40]))

(def empty-board '())

(defn safe? [col the-queens]
  (let [the-queen  (first the-queens)
        can-attack (fn [q1 q2]
                     (or (= (first q1) (first q2))  ; same row
                         (= (abs (- (first q1) (first q2)))
                            (abs (- (second q1) (second q2))))))]
    (loop [safe        true
           next-queens (rest the-queens)]
      (when safe
        (let [attacking-queen (first next-queens)]
          (if (nil? attacking-queen)
            safe
            (recur (not (can-attack attacking-queen the-queen))
                   (rest next-queens))))))))

(defn adjoin-position [row col board]
  (cons [row col] board))

(defn queen-cols [k board-size]
  (if (zero? k)
    (list empty-board)
    (filter
     (fn [positions] (safe? k positions))
     (e40/flatmap
      (fn [rest-of-queens]
        (map (fn [new-row]
               (adjoin-position new-row k rest-of-queens))
             (range 1 (inc board-size))))
      (queen-cols (dec k) board-size)))))

(defn queens [board-size]
  (queen-cols board-size board-size))

(queens 4)
