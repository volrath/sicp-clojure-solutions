(ns sicp.chapter2.exercise-02)

(defn make-segment [p1 p2]
  (vector p1 p2))

(defn start-segment [seg]
  (first seg))

(defn end-segment [seg]
  (second seg))

(defn make-point [x y]
  (vector x y))

(defn x-point [p]
  (first p))

(defn y-point [p]
  (second p))

(defn point-str [p]
  (str "(" (x-point p) ", " (y-point p) ")"))

(defn midpoint-segment [seg]
  (let [start (start-segment seg)
        end   (end-segment seg)
        mid-x (/ (+ (x-point end) (x-point start)) 2.0)
        mid-y (/ (+ (y-point end) (y-point start)) 2.0)]
    (make-point mid-x mid-y)))

(let [start (make-point 0 0)
      end   (make-point 5 5)
      seg   (make-segment start end)]
  (assert (= (point-str (midpoint-segment seg))
             "(2.5, 2.5)")))
