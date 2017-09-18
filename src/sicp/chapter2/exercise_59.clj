(ns sicp.chapter2.exercise-59)

(defn element-of-set? [x s]
  (cond (empty? s)      false
        (= x (first s)) true
        :else           (element-of-set? x (rest s))))

(defn union-set [s1 s2]
  (cond
    (empty? s2) s1
    (empty? s1) s2
    :else       (let [s1-car    (first s1)
                      sub-union (union-set (rest s1) s2)]
                  (if (element-of-set? s1-car sub-union)
                    sub-union
                    (cons s1-car sub-union)))))

(assert (= (union-set '(1 2 3) '(3 4 5))
           '(1 2 3 4 5)))
