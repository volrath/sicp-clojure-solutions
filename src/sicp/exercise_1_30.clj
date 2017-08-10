(ns sicp.exercise-1-30
  (:require [sicp.exercise-1-29 :as e29]))

(defn sum [f a next-t b]
  (loop [i      a
         result 0]
    (if (> i b)
      result
      (recur (next-t i)
             (+ result (f i))))))

(defn integral [f a b dx]
  (let [add-dx #(+ % dx)
        sumation (sum f (+ a (/ dx 2.0)) add-dx b)]
    (* sumation dx)))

;; (assert (= (e29/integral e29/cube 0 1 0.001 nil)
;;            (integral e29/cube 0 1 0.001)))
