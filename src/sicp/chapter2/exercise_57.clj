(ns sicp.chapter2.exercise-57
  (:require [sicp.chapter2.exercise-56 :as e56]))

(defn make-sum [& as]
  {:pre [(>= (count as) 2)]}
  (let [number-reduced-as (filter #(not (e56/=number? % 0))
                                  (cons (reduce + 0 (filter number? as))
                                        (filter (comp not number?) as)))]
    (case (count number-reduced-as)
      0     0
      1     (first number-reduced-as)
      (cons '+ number-reduced-as))))

(defn augend [e]
  {:pre [(seq? e) (>= (count e) 3)]}
  (let [as (drop 2 e)]
    (if (> (count as) 1)
      (apply make-sum as)
      (first as))))

(defn make-product [& ms]
  {:pre [(>= (count ms) 2)]}
  (let [number-reduced-ms (filter #(not (e56/=number? % 1))
                                  (cons (reduce * 1 (filter number? ms))
                                        (filter (comp not number?) ms)))]
    (case (count number-reduced-ms)
      0 1
      1 (first number-reduced-ms)
      (if (e56/=number? (first number-reduced-ms) 0)
        0
        (cons '* number-reduced-ms)))))

(defn multiplicand [e]
  {:pre [(seq? e) (>= (count e) 3)]}
  (let [ms (drop 2 e)]
    (if (> (count ms) 1)
      (apply make-product ms)
      (first ms))))

(defn deriv [exp var]
  (cond (number? exp)             0
        (e56/variable? exp)       (if (e56/same-variable? exp var) 1 0)
        (e56/sum? exp)            (make-sum (deriv (e56/addend exp) var)
                                            (deriv (augend exp) var))
        (e56/product? exp)        (make-sum
                                   (make-product (e56/multiplier exp)
                                                 (deriv (multiplicand exp) var))
                                   (make-product (deriv (e56/multiplier exp) var)
                                                 (multiplicand exp)))
        (e56/exponentiation? exp) (make-product
                                   (e56/exponent exp)
                                   (make-product
                                    (e56/make-exponentiation (e56/base exp) (dec (e56/exponent exp)))
                                    (deriv (e56/base exp) var)))
        :else                     (throw (Exception. (format "unknown expression type -- DERIV %s" exp)))))

(assert (= (deriv '(* x y (+ x 3)) 'x)
           (e56/deriv '(* (* x y) (+ x 3)) 'x)
           '(+ (* x y) (* y (+ x 3)))))
