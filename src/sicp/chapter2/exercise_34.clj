(ns sicp.chapter2.exercise-34
  (:require [clojure.math.numeric-tower :refer [abs expt]]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [sicp.chapter2.exercise-33 :as e33]))

(defn horner-eval [x coefficient-sequence]
  (e33/accumulate (fn [this-coef higher-terms]
                    (+ this-coef
                       (* higher-terms x)))
                  0
                  coefficient-sequence))

;; spec test

;; (defn simple-polynomial-eval [x coefficient-sequence]
;;   (loop [coeffs      coefficient-sequence
;;          exponential 0
;;          result      0]
;;     (let [coef (first coeffs)]
;;       (if (nil? coef)
;;         result
;;         (recur (rest coeffs)
;;                (inc exponential)
;;                (+ result (* coef (expt x exponential))))))))

;; (defn good-enough? [past current]
;;   (let [threshold 0.000001]
;;     (<= (abs (- current past)) (threshold current))))

;; (s/fdef horner-eval
;;         :args (s/cat :x number? :coefficients (s/coll-of integer?))
;;         :ret  number?
;;         :fn   #(good-enough? (:ret %) (simple-polynomial-eval (:x (:args %))
;;                                                               (:coefficients (:args %)))))

;; (stest/abbrev-result (first (stest/check `horner-eval)))
