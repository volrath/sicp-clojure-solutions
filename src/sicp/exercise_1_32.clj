(ns sicp.exercise-1-32)

(defn accumulate-recursive [combiner null-value f a next-t b]
  (if (> a b)
    null-value
    (combiner (f a)
              (accumulate-recursive combiner null-value f (next-t a) next-t b))))

(defn accumulate [combiner null-value f a next-t b]
  (loop [i      a
         result null-value]
    (if (> i b)
      null-value
      (recur (next-t i)
             (combiner result (f i))))))

(defn sum [f a next-t b]
  (accumulate + 0 f a next-t b))

(defn product [f a next-t b]
  (accumulate * 1 f a next-t b))
