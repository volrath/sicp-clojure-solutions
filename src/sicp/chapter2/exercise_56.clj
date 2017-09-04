(ns sicp.chapter2.exercise-56)

(def variable? symbol?)

(defn =number? [e n]
  {:pre [(number? n)]}
  (and (number? e) (= e n)))

(defn same-variable? [e1 e2]
  (and (variable? e1) (variable? e2) (= e1 e2)))

(defn sum? [e]
  (and (seq? e)
       (= (first e) '+)))

(defn addend [e]
  (second e))

(defn augend [e]
  (nth e 2))

(defn make-sum [a1 a2]
  (cond (=number? a1 0)                 a2
        (=number? a2 0)                 a1
        (and (number? a1) (number? a2)) (+ a1 a2)
        :else                           (list '+ a1 a2)))

(defn product? [e]
  (and (seq? e)
       (= (first e) '*)))

(defn multiplier [e]
  (second e))

(defn multiplicand [e]
  (nth e 2))

(defn make-product [m1 m2]
  (cond (or (=number? m1 0) (=number? m2 0)) 0
        (=number? m1 1)                      m2
        (=number? m2 1)                      m1
        (and (number? m1) (number? m2))      (* m1 m2)
        :else                                (list '* m1 m2)))

(defn exponentiation? [e]
  (and (seq? e)
       (= (first e) '**)
       (= (count e) 3)))

(defn base [e]
  (second e))

(defn exponent [e]
  (nth e 2))

(defn make-exponentiation [b e]
  (cond (=number? e 1) 0
        (=number? e 0) b
        :else (list '** b e)))

(defn deriv [exp var]
  (cond (number? exp) 0
        (variable? exp) (if (same-variable? exp var) 1 0)
        (sum? exp) (make-sum (deriv (addend exp) var)
                             (deriv (augend exp) var))
        (product? exp) (make-sum
                        (make-product (multiplier exp)
                                      (deriv (multiplicand exp) var))
                        (make-product (deriv (multiplier exp) var)
                                      (multiplicand exp)))
        (exponentiation? exp) (make-product
                               (exponent exp)
                               (make-product (make-exponentiation (base exp) (dec (exponent exp)))
                                             (deriv (base exp) var)))
        :else (throw (Exception. (format "unknown expression type -- DERIV %s" exp)))))

(assert (and (= (deriv '(+ x 3) 'x) 1)
             (= (deriv '(* x y) 'x) 'y)
             (= (deriv '(* (* x y) (+ x 3)) 'x) '(+ (* x y) (* y (+ x 3))))))

;; Actual exercise proof.

(assert (= (deriv '(* 3 (** x 3)) 'x) '(* 3 (* 3 (** x 2)))))
