(ns sicp.exercise-1-29
  (:require [sicp.visualizer :refer [graph-points]]))

(defn sum [f a next-t b acc-atom]
  (if (> a b)
    0
    (do
      (when acc-atom
        (swap! acc-atom conj {:x a :y (f a)}))
      (+ (f a)
         (sum f (next-t a) next-t b acc-atom)))))

(defn integral [f a b dx acc-atom]
  (let [add-dx #(+ % dx)
        sumation (sum f (+ a (/ dx 2.0)) add-dx b acc-atom)]
    (when acc-atom
      (reset! acc-atom (map (fn [p] (update p :y * dx)) @acc-atom)))
    (* sumation dx)))

(defn simpsons-rule [f a b n points-acc-atom]
  {:pre [(even? n) (> b a)]}
  (let [h (/ (- b a) n)
        y (fn [k] (f (+ a (* k h))))]
    (swap! points-acc-atom conj {:x a :y (* (/ h 3.0) (y 0))})
    (* (/ h 3.0)
       (+ (y 0)
          (loop [i   1
                 acc 0]
            (if (= i n)
              (do
                (swap! points-acc-atom conj {:x b :y (* (/ h 3.0) (y n))})
                (+ acc (y n)))
              (do
                (swap! points-acc-atom conj {:x (+ a (* i h))
                                             :y (* (/ h 3.0)
                                                   (* (if (odd? i) 4 2)
                                                      (y i)))})
                (recur (inc i)
                       (+ acc (* (if (odd? i) 4 2)
                                 (y i)))))))))))

(defn cube [x] (* x x x))

(let [n100-atom  (atom [])
      n1000-atom (atom [])
      intgr-atom (atom [])
      n100       (simpsons-rule cube 0 1 100 n100-atom)
      n1000      (simpsons-rule cube 0 1 1000 n1000-atom)
      intgr      (integral cube 0 1 0.01 intgr-atom)]
  (graph-points [@intgr-atom @n1000-points-acc @n100-points-acc])
  [n100 n1000 intgr])
