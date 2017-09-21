(ns sicp.chapter2.exercise-58
  (:require [sicp.chapter2.exercise-56 :as e56]))

;; Infix notation
;; a and b.

(defn sum? [e]
  (and (seq? e)
       (>= (count e) 3)
       ;; (every? #(= % '+) (take-nth 2 (rest e)))
       (some #(= % '+) (take-nth 2 (rest e)))))

(defn addend [e]
  {:pre [(sum? e)]}
  (let [ad (take-while #(not= % '+) e)]
    (if (= (count ad) 1)
      (first ad)
      ad)))

(defn augend [e]
  {:pre [(sum? e)]}
  (let [as (rest (drop-while #(not= % '+) e))]
    (if (= (count as) 1)
      (first as)
      as)))

(defn make-sum [& as]
  {:pre [(pos? (count as))]}
  (if (= (count as) 1)
    (first as)
    (let [ad (first as)
          au (apply make-sum (rest as))]
      (cond (e56/=number? ad 0)             au
            (e56/=number? au 0)             ad
            (and (number? ad) (number? au)) (+ ad au)
            :else                           (list ad '+ au)))))

(defn product? [e]
  (and (seq? e)
       (>= (count e) 3)
       ;; (every? #(= % '*) (take-nth 2 (rest e)))
       (= (second e) '*)))

(defn multiplier [e]
  {:pre [(product? e)]}
  (first e))

(defn multiplicand [e]
  {:pre [(product? e)]}
  (let [ms (drop 2 e)]
    (if (= (count ms) 1)
      (first ms)
      ms)))

(defn make-product [& ms]
  {:pre [(pos? (count ms))]}
  (if (= (count ms) 1)
    (first ms)
    (let [m1 (first ms)
          m2 (apply make-product (rest ms))]
      (cond (or (e56/=number? m1 0) (e56/=number? m2 0)) 0
            (e56/=number? m1 1)                          m2
            (e56/=number? m2 1)                          m1
            (and (number? m1) (number? m2))              (* m1 m2)
            :else                                        (list m1 '* m2)))))

(defn deriv-infix [exp var]
  (cond (number? exp)       0
        (e56/variable? exp) (if (e56/same-variable? exp var) 1 0)
        (sum? exp)          (make-sum (deriv-infix (addend exp) var)
                                      (deriv-infix (augend exp) var))
        (product? exp)      (make-sum
                             (make-product (multiplier exp)
                                           (deriv-infix (multiplicand exp) var))
                             (make-product (deriv-infix (multiplier exp) var)
                                           (multiplicand exp)))
        :else               (throw (Exception. (format "unknown expression type -- DERIV %s" exp)))))

(assert (and
         (= (deriv-infix '(x + (3 * (x + (y + 2)))) 'x)
            (deriv-infix '(x + 3 * (x + y + 2)) 'x)
            4)
         (= (deriv-infix '((x * x) + x) 'x)
            (deriv-infix '(x * x + x) 'x)
            '((x + x) + 1))))
