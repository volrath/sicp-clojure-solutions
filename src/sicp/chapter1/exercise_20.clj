(ns sicp.chapter1.exercise-20)

(defn gcd [a b]
  (if (zero? b)
    a
    (gcd b (rem a b))))

(defn gcd-explained [a b]
  (if (zero? b)
    `(gcd ~a ~b)
    (let [sub-gcd (gcd-explained b (rem a b))]
      `(gcd ~a ~sub-gcd))))

(gcd-explained 206 40)  ;; 5 applications of 'gcd
