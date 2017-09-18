(ns sicp.chapter2.exercise-61)

(defn adjoin-set [x set]
  (let [h (first set)]
    (cond (empty? set) (list x)
          (= x h) set
          (< x h) (cons x set)
          (> x h) (cons h (adjoin-set x (rest set))))))

(assert (and (= (adjoin-set 1 '(2 3 4)) '(1 2 3 4))
             (= (adjoin-set 2 '(1 3 4)) '(1 2 3 4))
             (= (adjoin-set 4 '(1 2 3)) '(1 2 3 4))
             (= (adjoin-set 1 '()) '(1))))
