(ns sicp.exercise-1-41)

(defn >double [g]
  (fn [x]
    (g (g x))))

((>double inc) 5)  ;; => 7
(((>double (>double >double)) inc) 5)  ;; => 21
