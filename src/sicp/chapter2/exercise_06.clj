(ns sicp.chapter2.exercise-06)

(def zero (fn [f] (fn [x] x)))

(defn succ [n]
  (fn [f] (fn [x] (f ((n f) x)))))

(def one  (fn [f] (fn [x] (f x))))
(def two  (fn [f] (fn [x] (f (f x)))))

(defn plus [n1 n2]
  (fn [f]
    (fn [x]
      ((n1 f) ((n2 f) x)))))

((zero inc) 0)
(assert (= (((plus one two) inc) 0) 3))
