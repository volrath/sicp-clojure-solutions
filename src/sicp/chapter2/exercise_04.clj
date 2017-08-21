(ns sicp.chapter2.exercise-04)

(defn >cons [x y]
  (fn [m] (m x y)))

(defn >car [z]
  (z (fn [p q] p)))

(defn >cdr [z]
  (z (fn [p q] q)))

(let [pair (>cons :a :b)]
  (assert (and (= (>car pair) :a)
               (= (>cdr pair) :b))))

;; -- explanation using substitution model

(defn >cons-explained [x y]
  (fn [m]
    (list m x y)))

(defn >cdr-explained [z]
  (z `(lambda (p q) q)))

(>cdr-explained (>cons-explained :a :b))  ;; => ((lambda (p q) q) :a :b)
