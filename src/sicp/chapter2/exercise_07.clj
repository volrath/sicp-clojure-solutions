(ns sicp.chapter2.exercise-07)

(defn parallel-resistance [r1 r2]
  (/ 1
     (+ (/ 1 r1)
        (/ 1 r2))))

(defn make-interval [lb ub]
  (vector lb ub))

(defn lower-bound [i]
  (first i))

(defn upper-bound [i]
  (second i))

(defn add-interval [i1 i2]
  (make-interval (+ (lower-bound i1) (lower-bound i2))
                 (+ (upper-bound i1) (upper-bound i2))))

(defn mul-interval [i1 i2]
  (let [p1 (* (lower-bound i1) (lower-bound i2))
        p2 (* (upper-bound i1) (lower-bound i2))
        p3 (* (lower-bound i1) (upper-bound i2))
        p4 (* (upper-bound i1) (upper-bound i2))]
    (make-interval (min p1 p2 p3 p4)
                   (max p1 p2 p3 p4))))

(defn div-interval [i1 i2]
  (mul-interval i1
                (make-interval (/ 1.0 (lower-bound i2))
                               (/ 1.0 (upper-bound i2)))))
