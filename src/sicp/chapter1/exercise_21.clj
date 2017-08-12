(ns sicp.chapter1.exercise-21)

(defn find-divisor [n test-divisor next-fn]
  (let [divides? (fn [a b] (zero? (rem b a)))
        square   (fn [n] (* n n))]
    (cond (> (square test-divisor) n) n
          (divides? test-divisor n)   test-divisor
          :else                       (find-divisor n (next-fn test-divisor) next-fn))))

(defn smallest-divisor [n]
  (find-divisor n 2 inc))

(map smallest-divisor [199 1999 19999])
