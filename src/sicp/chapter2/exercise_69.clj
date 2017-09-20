(ns sicp.chapter2.exercise-69
  (:require [sicp.chapter2.exercise-67 :as e67]))

(defn adjoin-set [x set]
  (cond (empty? set) (list x)
        (< (e67/weight x) (e67/weight (first set))) (cons x set)
        :else (cons (first set) (adjoin-set x (rest set)))))

(defn make-leaf-set [pairs]
  (if (empty? pairs)
    '()
    (let [[symbol weight] (first pairs)]
      (adjoin-set (e67/make-leaf symbol weight)
                  (make-leaf-set (rest pairs))))))

(defn successive-merge [leaf-set]
  (loop [lset leaf-set]
    (if (< (count lset) 2)
      (first lset)
      (let [lightest-leaf-1 (first lset)
            lightest-leaf-2 (second lset)
            merged-tree     (e67/make-code-tree lightest-leaf-1
                                                lightest-leaf-2)
            rest-leaf-set   (rest (rest lset))]
        (recur (adjoin-set merged-tree rest-leaf-set))))))

(defn generate-huffman-tree [pairs]
  (successive-merge (make-leaf-set pairs)))

(assert (= (generate-huffman-tree '((A 4) (B 2) (C 1) (D 1)))
           e67/sample-tree))
