(ns sicp.chapter2.exercise-06)

(def zero (fn [f] (fn [x] x)))

(defn add-1 [n]
  (fn [f] (fn [x] (f ((n f) x)))))

(def one (fn [f] (fn [x] (f x))))
(def two (fn [f] (fn [x] (f (f x)))))

(defn plus [n1 n2]
  (fn [f]
    (fn [x]
      ((n1 f) ((n2 f) x)))))

(assert (= (((plus one two) inc) 0) 3))
((zero inc) 0)
