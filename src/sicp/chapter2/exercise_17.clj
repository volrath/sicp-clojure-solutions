(ns sicp.chapter2.exercise-17)

(defn last-pair [lst]
  {:pre [(> (count lst) 0)]}
  (loop [l lst]
    (if (empty? (rest l))
      (first l)
      (recur (rest l)))))

(assert (and (= (last-pair '(1 2 3)) 3)
             (= (last-pair '(:a)) :a)))
