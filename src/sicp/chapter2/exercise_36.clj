(ns sicp.chapter2.exercise-36
  (:require [sicp.chapter2.exercise-33 :as e33]))

(defn accumulate-n [op initial seqs]
  (if (empty? (first seqs))
    '()
    (cons (e33/accumulate op initial (map first seqs))
          (accumulate-n op initial (map rest seqs)))))

(let [s '((1 2 3) (4 5 6) (7 8 9) (10 11 12))]
  (assert (= (accumulate-n + 0 s) '(22 26 30))))
