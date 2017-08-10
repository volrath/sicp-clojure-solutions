(ns sicp.exercise-1-39)

(defn tan-cf [x k]
  {:pre [(pos? k)]}
  (let [n #(if (= % 1) x (* x x))
        d #(- (* % 2) 1)]
    (cont-frac n d k)))
