(ns sicp.chapter2.exercise-62)

(defn union-set [s1 s2]
  (cond (empty? s1) s2
        (empty? s2) s1
        :else (let [h1 (first s1)
                    h2 (first s2)]
                (cond (= h1 h2) (cons h1 (union-set (rest s1) (rest s2)))
                      (< h1 h2) (cons h1 (union-set (rest s1) s2))
                      (> h1 h2) (cons h2 (union-set s1 (rest s2)))))))

(assert (= (union-set '(1 2 3 6) '(3 4 5))
           '(1 2 3 4 5 6)))
