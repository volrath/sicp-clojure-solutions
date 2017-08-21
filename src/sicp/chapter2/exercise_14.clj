(ns sicp.chapter2.exercise-14
  (:require [clojure.algo.generic.functor :refer [fmap]]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [sicp.chapter2.exercise-07 :as e07]
            [sicp.chapter2.exercise-10 :as e10]
            [sicp.chapter2.exercise-11 :as e11]
            [sicp.chapter2.exercise-12 :as e12]
            [sicp.chapter2.exercise-13 :as e13]))

(defn par1 [r1 r2]
  (e10/div-interval (e11/mul-interval r1 r2)
                    (e07/add-interval r1 r2)))

(defn par2 [r1 r2]
  (let [one (e07/make-interval 1 1)]
    (e10/div-interval one
                      (e07/add-interval (e10/div-interval one r1)
                                        (e10/div-interval one r2)))))

(defn center-percent [i]
  [(e12/center i) (e12/percent i)])

(defn lems-complaint []
  (let [A (first (gen/sample (s/gen ::e13/low-percentage-interval) 1))
        B (first (gen/sample (s/gen ::e13/low-percentage-interval) 1))
        results {:A     A
                 :B     B
                 :AdivB (e10/div-interval A B)
                 :AdivA (e10/div-interval A A)
                 :par1  (par1 A B)
                 :par2  (par2 A B)}]
    {:interval-results results
     :center-percent-results (fmap center-percent results)}))
