(ns sicp.chapter2.exercise-73
  (:require [sicp.chapter2.exercise-56 :as e56]))

(def dd-table (atom {}))

(defn dd-put [op type item]
  (swap! dd-table assoc-in [op type] item))

(defn dd-get [op type]
  (get-in @dd-table [op type]))

(defn operator [exp]
  (first exp))

(defn operands [exp]
  (rest exp))

(defn deriv [exp var]
  (cond (number? exp)       0
        (e56/variable? exp) (if (e56/same-variable? exp var) 1 0)
        :else               ((dd-get 'deriv (operator exp)) (operands exp) var)))

;; a.
;; Each arithmetic operation is being treated as a `type` of data to be
;; `deriv`ed, where `deriv` acts as the operation to be performed.
;; Numbers can't be treated the same way because, unlike the other expressions,
;; they are not "tagged". sums and multiplication expressions are "tagged" by
;; their operator, so the `type` is easily inferable.
;; In any case, it would be possible to abstract them from `deriv`, but the
;; logic would only be then hidden in `operator` and `operands`.

;; b.
;; This is not idiomatic in Clojure, but for the sake of keeping up with the
;; exercise.

(defn install-sum-package []
  (let [make-sum  e56/make-sum ;; because I'm lazy
        sum-deriv (fn [[x y] var]
                    (make-sum (deriv x var)
                              (deriv y var)))]
    (dd-put 'make-sum '+ make-sum)
    (dd-put 'deriv '+ sum-deriv)))

(defn make-sum [x y]
  ((dd-get 'make-sum '+) x y))

(defn install-product-package []
  (let [make-product e56/make-product  ;; because I'm lazy
        prod-deriv   (fn [[x y] var]
                       (make-sum (make-product x
                                               (deriv y var))
                                 (make-product (deriv x var)
                                               y)))]
    (dd-put 'make-product '* make-product)
    (dd-put 'deriv '* prod-deriv)))

(do  ;; this imperativeness feels dirty
  (reset! dd-table {})
  (install-sum-package)
  (install-product-package)
  (assert (and (= (deriv '(+ x 3) 'x) 1)
               (= (deriv '(* x y) 'x) 'y)
               (= (deriv '(* (* x y) (+ x 3)) 'x) '(+ (* x y) (* y (+ x 3)))))))

;; c.

(defn make-product [x y]
  ((dd-get 'make-product '*) x y))

(defn install-exponentiation-package []
  (let [make-exponentiation e56/make-exponentiation  ;; because I'm lazy
        expt-deriv (fn [[b e] var]
                     (make-product
                      e
                      (make-product (make-exponentiation b (dec e))
                                    (deriv b var))))]
    (dd-put 'deriv '** expt-deriv)))

(do
  (reset! dd-table {})
  (install-sum-package)
  (install-product-package)
  (install-exponentiation-package)
  (assert (= (deriv '(* 3 (** x 3)) 'x) '(* 3 (* 3 (** x 2))))))

;; d. Change `dd-put` statements use the new indexing order.
