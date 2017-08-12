(ns sicp.chapter1.exercise-40
  (:require [clojure.math.numeric-tower :refer [expt]]
            [sicp.chapter1.exercise-35 :as e35]))

(def dx 0.00001)

(defn deriv [g]
  (fn [x]
    (/ (- (g (+ x dx)) (g x))
       dx)))

;; ((deriv #(* % % %)) 5)

(defn newton-transform [g]
  (fn [x]
    (- x
       (/ (g x)
          ((deriv g) x)))))

(defn fixed-point-of-transform [g transform guess]
  (e35/fixed-point (transform g) guess))

(defn newton-method [g guess]
  (fixed-point-of-transform g newton-transform guess))

(defn newton-sqrt [x]
  (let [square #(* % %)]
    (fixed-point-of-transform (fn [y] (- (square y) x)) newton-transform 1.0)))

;; (newton-sqrt 25)

(defn cubic [a b c]
  (fn [x]
    (+ (expt x 3)
       (* a (expt x 2))
       (* b x)
       c)))

(newton-method (cubic 1 2 3) 1)
