(ns sicp.chapter2.exercise-79
  (:require [sicp.chapter2.exercise-77 :as e77 :refer [with-generic-arithmetic
                                                       install-complex-rectangular-package
                                                       install-complex-polar-package
                                                       install-complex-package
                                                       install-rational-package
                                                       install-scheme-number-package]]))

;; This works with the initial `scheme-number` package
(defn install-generic-equality-pred-package [dd-table]
  (let [real-part (fn [z] (e77/apply-generic dd-table 'real-part z))
        imag-part (fn [z] (e77/apply-generic dd-table 'imag-part z))

        eq-scheme-number (fn [x y] (= x y))
        eq-rational      (fn [x y] (and (= (first x) (first y))
                                       (= (second x) (second y))))
        eq-complex       (fn [z1 z2] (and (= (real-part z1) (real-part z2))
                                         (= (imag-part z1) (imag-part z2))))]
    (-> dd-table
        (e77/dd-put 'equ '(scheme-number scheme-number) eq-scheme-number)
        (e77/dd-put 'equ '(rational rational) eq-rational)
        (e77/dd-put 'equ '(complex complex) eq-complex))))

(with-generic-arithmetic
  :packages :default
  (let [dd-table                    (install-generic-equality-pred-package dd-table)
        make-scheme-number          (e77/dd-get dd-table 'make 'scheme-number)
        make-rat                    (e77/dd-get dd-table 'make 'rational)
        make-complex-from-real-imag (e77/dd-get dd-table 'make-from-real-imag 'complex)
        make-complex-from-mag-ang   (e77/dd-get dd-table 'make-from-mag-ang 'complex)

        equ                         (fn [x y] (e77/apply-generic dd-table 'equ x y))]
    (assert (equ (make-scheme-number 6)
                 (make-scheme-number 6)))
    (assert (equ (make-rat 1 2)
                 (make-rat 4 8)))
    (assert (equ (make-complex-from-real-imag Math/PI 0.0)
                 (make-complex-from-mag-ang Math/PI 0)))))
