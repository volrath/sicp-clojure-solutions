(ns sicp.chapter2.exercise-77
  (:require [clojure.math.numeric-tower :refer [sqrt]]
            [quil.core :refer [atan]]
            [sicp.chapter1.exercise-20 :as c1e20]))

;;
;; This is a bit more functional than exercise 73 but still not quite idiomatic
;; Closure.
;;

(def square #(* % %))

;; Dynamic data helpers

(defn dd-get [table op type]
  (get-in table [op type]))

(defn dd-put [table op type item]
  (assoc-in table [op type] item))

(defn attach-tag [tag x]
  [tag x])

(defn tagged? [x]
  (and (vector? x)
       (symbol? (first x))))

(defn type-tag [x]
  {:pre  [(tagged? x)]
   :post [(symbol? %)]}
  (first x))

(defn contents [x]
  {:pre [(tagged? x)]}
  (second x))

(defn apply-generic [dd-table op & args]
  (let [type-tags (map type-tag args)
        proc      (dd-get dd-table op type-tags)]
    (if proc
      (apply proc (map contents args))
      (throw (ex-info (format "No generic method found.")
                      {:op op :types type-tags})))))

;; Packages

(defn install-scheme-number-package [dd-table]
  (let [tag (fn [x] (attach-tag 'scheme-number x))]
    (-> dd-table
        (dd-put 'add '(scheme-number scheme-number)
                (fn [x y] (tag (+ x y))))
        (dd-put 'sub '(scheme-number scheme-number)
                (fn [x y] (tag (- x y))))
        (dd-put 'mul '(scheme-number scheme-number)
                (fn [x y] (tag (* x y))))
        (dd-put 'div '(scheme-number scheme-number)
                (fn [x y] (tag (/ x y))))
        (dd-put 'make 'scheme-number
                (fn [x] (tag x))))))

(defn install-rational-package [dd-table]
  (let [numer    (fn [x] (first x))
        denom    (fn [x] (second x))
        make-rat (fn [n d]
                   (let [g (c1e20/gcd n d)]
                     [(/ n g) (/ d g)]))
        add-rat  (fn [x y]
                   (make-rat (+ (* (numer x) (denom y))
                                (* (numer y) (denom x)))
                             (* (denom x) (denom y))))
        sub-rat  (fn [x y]
                   (make-rat (- (* (numer x) (denom y))
                                (* (numer y) (denom x)))
                             (* (denom x) (denom y))))
        mul-rat  (fn [x y]
                   (make-rat (* (numer x) (numer y))
                             (* (denom x) (denom y))))
        div-rat  (fn [x y]
                   (make-rat (* (numer x) (denom y))
                             (* (denom x) (numer y))))
        tag      (fn [x] (attach-tag 'rational x))]
    ;; Interface to rest of the system
    (-> dd-table
        (dd-put 'add '(rational rational)
                (fn [x y] (tag (add-rat x y))))
        (dd-put 'sub '(rational rational)
                (fn [x y] (tag (sub-rat x y))))
        (dd-put 'mul '(rational rational)
                (fn [x y] (tag (mul-rat x y))))
        (dd-put 'div '(rational rational)
                (fn [x y] (tag (div-rat x y))))
        (dd-put 'make 'rational
                (fn [n d] (tag (make-rat n d)))))))

(defn install-complex-rectangular-package [dd-table]
  (let [real-part           (fn [z] (first z))
        imag-part           (fn [z] (second z))
        make-from-real-imag (fn [x y] [x y])
        magnitude           (fn [z] (+ (square (real-part z))
                                      (square (imag-part z))))
        angle               (fn [z] (atan (imag-part z)
                                         (real-part z)))
        make-from-mag-ang   (fn [r a] [(* r (Math/cos a))
                                      (* r (Math/sin a))])
        tag                 (fn [x] (attach-tag 'rectangular x))]
    (-> dd-table
        (dd-put 'real-part '(rectangular) real-part)
        (dd-put 'imag-part '(rectangular) imag-part)
        (dd-put 'magnitude '(rectangular) magnitude)
        (dd-put 'angle '(rectangular) angle)
        (dd-put 'make-from-real-imag 'rectangular
                (fn [x y]
                  (tag (make-from-real-imag x y))))
        (dd-put 'make-from-mag-ang 'rectangular
                (fn [r a]
                  (tag (make-from-mag-ang r a)))))))

(defn install-complex-polar-package [dd-table]
  (let [magnitude           (fn [z] (first z))
        angle               (fn [z] (second z))
        make-from-mag-ang   (fn [r a] [r a])
        real-part           (fn [z] (* (magnitude z) (Math/cos (angle z))))
        imag-part           (fn [z] (* (magnitude z) (Math/sin (angle z))))
        make-from-real-imag (fn [x y] [(sqrt (+ (square x) (square y)))
                                      (atan x y)])
        tag                 (fn [x] (attach-tag 'polar x))]
    (-> dd-table
        (dd-put 'real-part '(polar) real-part)
        (dd-put 'imag-part '(polar) imag-part)
        (dd-put 'magnitude '(polar) magnitude)
        (dd-put 'angle '(polar) angle)
        (dd-put 'make-from-real-imag 'polar
                (fn [x y] (tag (make-from-real-imag x y))))
        (dd-put 'make-from-mag-ang 'polar
                (fn [r a] (tag (make-from-mag-ang r a)))))))

(defn install-complex-package-incomplete [dd-table]
  (let [make-from-real-imag (fn [x y]
                              ((dd-get dd-table 'make-from-real-imag 'rectangular) x y))
        make-from-mag-ang   (fn [r a]
                              ((dd-get dd-table 'make-from-mag-ang 'polar) r a))
        real-part           (fn [z]
                              (apply-generic dd-table 'real-part z))
        imag-part           (fn [z]
                              (apply-generic dd-table 'imag-part z))
        magnitude           (fn [z]
                              (apply-generic dd-table 'magnitude z))
        angle               (fn [z]
                              (apply-generic dd-table 'angle z))

        ;; Internal package procedures
        add-complex (fn [z1 z2]
                      (make-from-real-imag (+ (real-part z1) (real-part z2))
                                           (+ (imag-part z1) (real-part z2))))
        sub-complex (fn [z1 z2]
                      (make-from-real-imag (- (real-part z1) (real-part z2))
                                           (- (imag-part z1) (real-part z2))))
        mul-complex (fn [z1 z2]
                      (make-from-mag-ang (* (magnitude z1) (magnitude z2))
                                         (+ (angle z1) (angle z2))))
        div-complex (fn [z1 z2]
                      (make-from-mag-ang (/ (magnitude z1) (magnitude z2))
                                         (- (angle z1) (angle z2))))
        tag         (fn [x] (attach-tag 'complex x))]
    (-> dd-table
        install-complex-rectangular-package
        install-complex-polar-package
        (dd-put 'add '(complex complex)
                (fn [z1 z2] (tag (add-complex z1 z2))))
        (dd-put 'sub '(complex complex)
                (fn [z1 z2] (tag (sub-complex z1 z2))))
        (dd-put 'mul '(complex complex)
                (fn [z1 z2] (tag (mul-complex z1 z2))))
        (dd-put 'div '(complex complex)
                (fn [z1 z2] (tag (div-complex z1 z2))))
        (dd-put 'make-from-real-imag 'complex
                (fn [x y] (tag (make-from-real-imag x y))))
        (dd-put 'make-from-mag-ang 'complex
                (fn [r a] (tag (make-from-mag-ang r a)))))))

(defn install-complex-package [dd-table]
  (let [real-part           (fn [z]
                              (apply-generic dd-table 'real-part z))
        imag-part           (fn [z]
                              (apply-generic dd-table 'imag-part z))
        magnitude           (fn [z]
                              (apply-generic dd-table 'magnitude z))
        angle               (fn [z]
                              (apply-generic dd-table 'angle z))]
    (-> dd-table
        install-complex-package-incomplete
        (dd-put 'real-part '(complex) real-part)
        (dd-put 'imag-part '(complex) imag-part)
        (dd-put 'magnitude '(complex) magnitude)
        (dd-put 'angle '(complex) angle))))

;; Generic arithmetic operations

(defmacro with-generic-arithmetic [_ package-list & body]
  (let [packages (if (= package-list :default)
                   (quote (list
                           install-complex-rectangular-package
                           install-complex-polar-package
                           install-complex-package
                           install-rational-package
                           install-scheme-number-package))
                   package-list)]
    `(let [~'dd-table (reduce (fn [table# install-pkg-fn#]
                                (install-pkg-fn# table#))
                              {}
                              ~packages)
           ~'add      (fn [x# y#] (apply-generic ~'dd-table (quote add) x# y#))
           ~'sub      (fn [x# y#] (apply-generic ~'dd-table (quote sub) x# y#))
           ~'mul      (fn [x# y#] (apply-generic ~'dd-table (quote mul) x# y#))
           ~'div      (fn [x# y#] (apply-generic ~'dd-table (quote div) x# y#))]
       ~@body)))

;; Actual exercise...

;; Here, magnitude is defined as in the book prior this exercise.  We are
;; installing the "incomplete" complex package defined so far, that doesn't
;; include complex number selectors in it.
(with-generic-arithmetic
  :packages (list
             install-complex-rectangular-package
             install-complex-polar-package
             install-complex-package-incomplete  ;; <- incomplete!
             install-rational-package
             install-scheme-number-package)
  (let [make-from-mag-ang (dd-get dd-table 'make-from-mag-ang 'complex)
        magnitude         (fn [z] (apply-generic dd-table 'magnitude z))]
    (try
      (magnitude (make-from-mag-ang 3 5))
      (assert false)  ;; just making sure the previous statement failed.
      (catch clojure.lang.ExceptionInfo e
        (assert (let [exc-data (ex-data e)]
                  (and (= (exc-data :op) 'magnitude)
                       (= (exc-data :types) '(complex)))))))))

;; Alyssa is right.  Now we try with the complete complex package that does
;; define (dd-put) complex number selectors in the table for '(complex).
(with-generic-arithmetic
  :packages :default
  (let [make-from-mag-ang (dd-get dd-table 'make-from-mag-ang 'complex)
        magnitude         (fn [z] (apply-generic dd-table 'magnitude z))]
    (assert (= (magnitude (make-from-mag-ang 3 5))
               3))))

;; => (magnitude (make-from-mag-ang 3 5))
;; => (magnitude [complex [polar [3 5]]])
;; => ((apply-generic dd-table 'magnitude [complex [polar [3 5]]]) [complex [polar [3 5]]])
;; => ;; This `apply-generic` implies doing a: (dd-get dd-table 'magnitude '(complex))
;; => ;; In the first dd-table,  this entry doesn't exist. Let's continue in the second example.
;; => (apply (fn [z] (apply-generic dd-table 'magnitude z)) (map contents [complex [polar [3 5]]]))
;; => (apply (fn [z] (apply-generic dd-table 'magnitude z)) '([polar [3 5]]))
;; => (apply-generic dd-table 'magnitude [polar [3 5]])
;; => ;; This one does exist. It's defined in `install-complex-polar-package`
;; => (apply (fn [z] (first z)) (map contents [polar [3 5]]))
;; => (apply (fn [z] (first z)) '([3 5]))
;; => 3
