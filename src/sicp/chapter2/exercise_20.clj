(ns sicp.chapter2.exercise-20
  (:require [sicp.chapter2.exercise-18 :as e18]))

(defn same-parity [& numbers]
  (let [filter-fn (if (even? (first numbers)) even? odd?)]
    (loop [to-filter (rest numbers)
           filtered  (list (first numbers))]
      (cond (empty? to-filter)             filtered
            (filter-fn (first to-filter)) (recur (rest to-filter)
                                                 (e18/append filtered (list (first to-filter))))
            :else                         (recur (rest to-filter) filtered)))))

(same-parity 1 2 3 4 5 6 7)  ;; => 1 3 5 7
(same-parity 2 3 4 5 6 7 8)  ;; => 2 4 6 8
