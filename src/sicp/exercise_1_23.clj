(ns sicp.exercise-1-23
  (:require [sicp.exercise-1-21 :as e21]
            [sicp.exercise-1-22 :as e22]))

(defn smallest-divisor [n]
  (let [next-divisor #(if (= % 2) 3 (+ % 2))]
    (e21/find-divisor n 2 next-divisor)))

(defn prime? [n]
  (= (smallest-divisor n) n))

(defn avg [numbers]
  (double (/ (apply + numbers) (count numbers))))

(defn timed-prime-test [n prime?-fn]
  (println n)
  (let [start (. System (nanoTime))]
    (when (prime?-fn n)
      (let [end   (. System (nanoTime))
            msecs (/ (double (- end start)) 1000000.0)]
        (println (str "*** " msecs " msecs"))
        msecs))))

(defn search-for-primes [prime?-fn a b max-count]
  {:pre [(odd? a)]}
  (println (str "searching between " a " and " b " with a max count of " max-count))
  (loop [n      a
         primes []]
    (if (or (> n b)
            (>= (count primes) max-count))
      primes
      (recur (+ n 2)
             (if-let [prime-n (timed-prime-test n prime?-fn)]
               (conj primes prime-n)
               primes)))))

(defn compare-avg-times
  "Returns the speed AVG in msecs between the two smallest-divisors calculating
  with the beginning of a range."
  [base prime?-base-fn prime?-competing-fn]
  (let [floor           (inc base)
        ceil            (* base 10)
        base-msecs      (search-for-primes prime?-base-fn floor ceil 3)
        competing-msecs (search-for-primes prime?-competing-fn floor ceil 3)
        base-avg        (avg base-msecs)
        competing-avg   (avg competing-msecs)]
    (/ (* competing-avg 100) base-avg)))

(let [avg-1000    (compare-avg-times 1000 e22/prime? prime?)
      avg-10000   (compare-avg-times 10000 e22/prime? prime?)
      avg-100000  (compare-avg-times 100000 e22/prime? prime?)
      avg-1000000 (compare-avg-times 1000000 e22/prime? prime?)]
  {:1000 avg-1000
   :10000 avg-10000
   :100000 avg-100000
   :1000000 avg-1000000
   :total (/ (+ avg-1000 avg-10000 avg-100000 avg-1000000) 4)})
