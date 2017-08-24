(ns sicp.chapter2.exercise-33)

(defn accumulate [op initial sequence]
  (if (empty? sequence)
    initial
    (op (first sequence)
        (accumulate op initial (rest sequence)))))

(defn >map [f sequence]
  (accumulate (fn [el rst] (cons (f el) rst))
              nil
              sequence))

(assert (= (>map inc '(0 1 2 3)) '(1 2 3 4)))


(defn append [seq1 seq2]
  (accumulate cons seq2 seq1))

(assert (= (append '(1 2) '(3 4)) '(1 2 3 4)))

(defn >length [sequence]
  (accumulate (fn [_ acc] (inc acc)) 0 sequence))

(assert (= (>length '(1 2 3 4)) 4))
