(ns sicp.chapter2.exercise-18
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(defn append [list1 list2]
  (if (empty? list1)
    list2
    (cons (first list1) (append (rest list1) list2))))

(defn >reverse [lst]
  (if (empty? (rest lst))
    lst
    (append (>reverse (rest lst)) (list (first lst)))))

;; spec testing

(s/fdef >reverse
        :args (s/cat :list (s/coll-of number?))
        :ret  (s/coll-of number?)
        :fn   #(= (:ret %) (reverse (-> % :args :list))))

(stest/abbrev-result (first (stest/check `>reverse)))
