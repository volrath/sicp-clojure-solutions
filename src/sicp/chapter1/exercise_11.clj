(ns sicp.exercise-11)

(defn f-rec [n]
  (if (< n 3)
    n
    (+ (f-rec (- n 1))
       (* 2 (f-rec (- n 2)))
       (* 3 (f-rec (- n 3))))))

(f-rec 5)

(defn f-iter-iter [acc1 acc2 acc3 n]
  (cond (= n 3) (+ acc1 (* 2 acc2) (* 3 acc3))
        :else (f-iter-iter (+ acc1 (* 2 acc2) (* 3 acc3))
                           acc1
                           acc2
                           (dec n))))

(defn f-iter [n]
  (if (< n 3)
    n
    (f-iter-iter 2 1 0 n)))

(f-iter 5)
