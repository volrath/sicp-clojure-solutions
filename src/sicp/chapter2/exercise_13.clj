(ns sicp.chapter2.exercise-13
  (:require [clojure.math.numeric-tower :refer [abs]]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.spec.test.alpha :as stest]
            [sicp.chapter2.exercise-07 :as e07]
            [sicp.chapter2.exercise-11 :as e11]
            [sicp.chapter2.exercise-12 :as e12]))

;; Reasoning in notebook.

(defn mul-interval-approx [i1 i2]
  (let [c1 (e12/center i1)
        p1 (e12/percent i1)
        c2 (e12/center i2)
        p2 (e12/percent i2)]
    (e12/make-center-percent (* c1 c2) (+ p1 p2))))

;; spec

(defn low-percentage-interval-gen []
  (->> (s/gen (s/tuple (s/double-in :min 1 :max 1000)
                       (s/double-in :min 0.001 :max 0.1)))
       (gen/fmap (fn [[c p]] (e12/make-center-percent c p)))))

(s/def ::low-percentage-interval (s/with-gen ::e11/interval low-percentage-interval-gen))

(s/fdef mul-interval-approx
        :args (s/cat :i1 ::low-percentage-interval :i2 ::low-percentage-interval)
        :ret  ::e11/long-interval
        :fn   #(let [[rl ru] (:ret %)
                     [ml mu] (e07/mul-interval (-> % :args :i1) (-> % :args :i2))
                     t       0.15]
                 (and (<= (abs (- ru mu)) t)
                      (<= (abs (- rl ml)) t))))

(let [val     {:args {:i1 [7.92 8.08], :i2 [126.11865234375 128.66650390625]}, :ret [998.7578125 1039.5234375]}
      [rl ru] (:ret val)
      [ml mu] (e07/mul-interval (-> val :args :i1) (-> val :args :i2))
      t       0.1]
  {:r    [rl ru]
   :m    [ml mu]
   :du   (- ru mu)
   :dl   (- rl ml)
   :pass (and (<= (abs (- ru mu)) t)
              (<= (abs (- rl ml)) t))})

(stest/abbrev-result (first (stest/check `mul-interval-approx)))
