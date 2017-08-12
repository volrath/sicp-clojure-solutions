(ns sicp.chapter1.exercise-22
  (:require [sicp.chapter1.exercise-21 :refer [smallest-divisor]]))

(defn prime? [n]
  (= (smallest-divisor n) n))

(defn timed-prime-test [n prime?-fn]
  (println n)
  (let [start (. System (nanoTime))]
    (when (prime?-fn n)
      (let [end   (. System (nanoTime))
            msecs (/ (double (- end start)) 1000000.0)]
        (println (str "*** " msecs " msecs"))
        n))))

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

(search-for-primes prime? 1001 10000 3)
(search-for-primes prime? 10001 100000 3)
(search-for-primes prime? 100001 1000000 3)
(search-for-primes prime? 1000001 10000000 3)

;; (* (sqrt 10) 0.002069)  ; it holds
