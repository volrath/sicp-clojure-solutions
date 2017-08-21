(ns sicp.chapter2.exercise-11
  (:require [clojure.math.numeric-tower :refer [abs]]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [sicp.chapter2.exercise-07 :as e07]))

(defn sign-pair [l u]
  (cond (>= 0 u l)            -1
        (and (< l 0) (> u 0)) 0
        (<= 0 l u)            1))

(defn mul-interval [i1 i2]
  (let [l-i1    (e07/lower-bound i1)
        u-i1    (e07/upper-bound i1)
        l-i2    (e07/lower-bound i2)
        u-i2    (e07/upper-bound i2)
        i1-sign (sign-pair l-i1 u-i1)
        i2-sign (sign-pair l-i2 u-i2)]
    (cond (neg? i1-sign)
          (cond (neg? i2-sign)  (e07/make-interval (* u-i1 u-i2) (* l-i1 l-i2))      ; ----
                (zero? i2-sign) (e07/make-interval (* l-i1 u-i2) (* l-i1 l-i2))      ; ---+
                (pos? i2-sign)  (e07/make-interval (* l-i1 u-i2) (* u-i1 l-i2)))     ; --++
          (zero? i1-sign)
          (cond (neg? i2-sign)  (e07/make-interval (* u-i1 l-i2) (* l-i1 l-i2))      ; -+--
                (zero? i2-sign) (e07/make-interval (min (* l-i1 u-i2) (* u-i1 l-i2)) ; -+-+
                                                   (max (* l-i1 l-i2) (* u-i1 u-i2)))
                (pos? i2-sign)  (e07/make-interval (* l-i1 u-i2) (* u-i1 u-i2)))     ; -+++
          (pos? i1-sign)
          (cond (neg? i2-sign)  (e07/make-interval (* u-i1 l-i2) (* l-i1 u-i2))      ; ++--
                (zero? i2-sign) (e07/make-interval (* u-i1 l-i2) (* u-i1 u-i2))      ; ++-+
                (pos? i2-sign)  (e07/make-interval (* l-i1 l-i2) (* u-i1 u-i2))))))  ; ++++

;; spec

(s/def ::interval (s/and (s/tuple (s/double-in :NaN? false :infinite? false)
                                  (s/double-in :NaN? false :infinite? false))
                         #(<= (first %) (second %))))
(s/def ::short-interval (s/and ::interval
                               #(<= (abs (first %)) 1000)
                               #(<= (abs (second %)) 1000)))
(s/def ::long-interval (s/and ::interval
                              #(<= (abs (first %)) 1000000)
                              #(<= (abs (second %)) 1000000)))

(s/fdef mul-interval
        :args (s/cat :i1 ::short-interval :i2 ::short-interval)
        :ret  ::long-interval
        :fn   #(= (:ret %) (e07/mul-interval (-> % :args :i1) (-> % :args :i2))))  ;; results should be equal to e07/mul-interval

(stest/abbrev-result (first (stest/check `mul-interval)))
