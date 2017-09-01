(ns sicp.chapter2.exercise-44)

(defn beside [left right]
  )

(defn below [down-painter up-painter]
  )

(defn up-split [painter n]
  (if (zero? n)
    painter
    (let [smaller (up-split painter (dec n))]
      (below painter (beside smaller smaller)))))
