(ns sicp.chapter2.exercise-80
  (:require [sicp.chapter2.exercise-77 :as e77 :refer [with-generic-arithmetic
                                                       install-complex-rectangular-package
                                                       install-complex-polar-package
                                                       install-complex-package
                                                       install-rational-package
                                                       install-scheme-number-package]]))

;; This works with the initial `scheme-number` package
(defn install-generic-zero-pred-package [dd-table]
  (let [real-part (fn [z] (e77/apply-generic dd-table 'real-part z))
        imag-part (fn [z] (e77/apply-generic dd-table 'imag-part z))

        =zero?-scheme-number (fn [x] (zero? x))
        =zero?-rational      (fn [x] (zero? (first x)))
        =zero?-complex       (fn [z] (and (zero? (real-part z))
                                         (zero? (imag-part z))))]
    (-> dd-table
        (e77/dd-put '=zero? '(scheme-number) =zero?-scheme-number)
        (e77/dd-put '=zero? '(rational) =zero?-rational)
        (e77/dd-put '=zero? '(complex) =zero?-complex))))

(with-generic-arithmetic
  :packages :default
  (let [dd-table                    (install-generic-zero-pred-package dd-table)
        make-scheme-number          (e77/dd-get dd-table 'make 'scheme-number)
        make-rat                    (e77/dd-get dd-table 'make 'rational)
        make-complex-from-real-imag (e77/dd-get dd-table 'make-from-real-imag 'complex)
        make-complex-from-mag-ang   (e77/dd-get dd-table 'make-from-mag-ang 'complex)

        =zero?                         (fn [x] (e77/apply-generic dd-table '=zero? x))]
    (assert (=zero? (make-scheme-number 0)))
    (assert (=zero? (make-rat 0 2)))
    (assert (=zero? (make-complex-from-real-imag 0 0)))))
