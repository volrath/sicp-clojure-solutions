(ns sicp.chapter2.exercise-19)

(def no-more? empty?)
(def first-denomination first)
(def except-first-denomination rest)

(defn cc [amount coin-values]
  (cond (zero? amount) 1
        (or (neg? amount) (no-more? coin-values)) 0
        :else (+ (cc amount
                     (except-first-denomination coin-values))
                 (cc (- amount (first-denomination coin-values))
                     coin-values))))

(def us-coins '(50 25 10 5 1))
(def uk-coins '(100 50 20 10 5 2 1 0.5))

(assert (and (= (cc 100 us-coins) 292)
             (= (cc 100 uk-coins) 104561)))

(let [unordered-us-coins '(1 10 25 5 50)]
  (cc 100 unordered-us-coins))  ;; => 292
