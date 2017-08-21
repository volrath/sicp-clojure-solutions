(ns sicp.chapter2.exercise-23)

(defn for-each [f items]
  (if (empty? items)
    nil
    (cons (f (first items))
          (for-each f (rest items)))))

(for-each println '(1 2 3))
