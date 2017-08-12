(ns sicp.chapter1.exercise-18)

(def double #(* % 2))
(def halve #(/ % 2))

(defn iter-mul [acc a b]
  (cond (zero? b) acc
        (even? b) (iter-mul acc (double a) (halve b))
        :else     (iter-mul (+ acc a) a (dec b))))

(defn fast-mul-iter [a b]
  (iter-mul 0 a b))

(fast-mul-iter 8 7)
