(ns sicp.chapter2.exercise-21)

(def square #(* % %))

(defn square-list-1 [lst]
  (if (empty? lst)
    nil
    (cons (square (first lst)) (square-list-1 (rest lst)))))

(defn square-list-2 [lst]
  (map square lst))

(assert (= (square-list-1 '(1 2 3 4))
           (square-list-2 '(1 2 3 4))))
