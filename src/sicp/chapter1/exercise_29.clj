(ns sicp.chapter1.exercise-29
  (:require [sicp.visualizer :refer [graph-points]]))

(defn sum [f a next-t b acc-atom]
  (if (> a b)
    0
    (do
      (when acc-atom
        (swap! acc-atom update :points conj {:x a :y (f a)}))
      (+ (f a)
         (sum f (next-t a) next-t b acc-atom)))))

(defn integral [f a b dx acc-atom]
  (let [add-dx #(+ % dx)
        sumation (sum f (+ a (/ dx 2.0)) add-dx b acc-atom)]
    (when acc-atom
      (reset! acc-atom {:points (map (fn [p] (update p :y * dx)) @acc-atom)}))
    (* sumation dx)))

(defn simpsons-rule [f a b n points-acc-atom]
  {:pre [(even? n) (> b a)]}
  (let [h (/ (- b a) n)
        y (fn [k] (f (+ a (* k h))))]
    (swap! points-acc-atom update :points conj {:x a :y (* (/ h 3.0) (y 0))})
    (* (/ h 3.0)
       (+ (y 0)
          (loop [i   1
                 acc 0]
            (if (= i n)
              (do
                (swap! points-acc-atom update :points conj {:x b :y (* (/ h 3.0) (y n))})
                (+ acc (y n)))
              (do
                (swap! points-acc-atom update :points conj {:x (+ a (* i h))
                                                            :y (* (/ h 3.0)
                                                                  (* (if (odd? i) 4 2)
                                                                     (y i)))})
                (recur (inc i)
                       (+ acc (* (if (odd? i) 4 2)
                                 (y i)))))))))))


(defn cube [x] (* x x x))

;; (simpsons-rule cube 0 1 1000)

(let [n100-atom  (atom {:points []})
      n1000-atom (atom {:points []})
      intgr-atom (atom {:points []})
      n100       (simpsons-rule cube 0 1 100 n100-atom)
      n1000      (simpsons-rule cube 0 1 1000 n1000-atom)
      intgr      (integral cube 0 1 0.01 intgr-atom)]
  (graph-points @n1000-atom @n100-atom)
  [n100 n1000 intgr])
