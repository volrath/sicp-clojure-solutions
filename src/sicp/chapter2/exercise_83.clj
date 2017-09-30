(ns sicp.chapter2.exercise-83
  (:require [sicp.chapter2.exercise-77 :as e77]))

(defn install-raise-package [dd-table]
  (let [make-rat     (e77/dd-get dd-table 'make 'rational)
        numer        (fn [r] (first (second r)))
        denom        (fn [r] (second (second r)))
        make-complex (e77/dd-get dd-table 'make-from-real-imag 'complex)

        int->rational  (fn [i] (make-rat i 1))
        rational->real (fn [r] (float (/ (numer r) (denom r))))
        real->complex  (fn [r] (make-complex r 0.0))]
    (-> dd-table
        (e77/dd-put 'raise '(integer) int->rational)
        (e77/dd-put 'raise '(rational) rational->real)
        (e77/dd-put 'raise '(real) real->complex))))
