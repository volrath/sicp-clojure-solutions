(ns sicp.chapter1.exercise-27)

(def square #(* % %))

(defn expmod [base exp m]
  (cond (zero? exp) 1
        (even? exp) (rem (square (expmod base (/ exp 2) m)) m)
        :else       (rem (* base (expmod base (- exp 1) m)) m)))

(defn fermat-test [n]
  (let [try-it (fn [a] (= (expmod a n n) a))]
    (loop [c      1
           prime? true]
      (let [next-c (inc c)]
        (if (or (not prime?) (>= next-c n))
          prime?
          (recur next-c (try-it next-c)))))))

(assert (every? fermat-test '(561 1105 1729 2465 2821 6601)))
