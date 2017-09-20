(ns sicp.chapter2.exercise-67
  (:require [sicp.chapter2.exercise-33 :as e33]))

;; Leafs
;; -----

(defn make-leaf [symbol weight]
  (list 'leaf symbol weight))

(defn leaf? [n]
  (= 'leaf (first n)))

(assert (leaf? (make-leaf 'test 1)))

(defn symbol-leaf [x]
  (second x))

(defn weight-leaf [x]
  (nth x 2))

;; Trees
;; -----

(defn left-branch [tree]
  (first tree))

(defn right-branch [tree]
  (second tree))

(defn weight [tree]
  (if (leaf? tree)
    (weight-leaf tree)
    (nth tree 3)))

(defn symbols [tree]
  (if (leaf? tree)
    (list (symbol-leaf tree))
    (nth tree 2)))

(defn make-code-tree [left right]
  (list left
        right
        (e33/append (symbols left) (symbols right))
        (+ (weight left) (weight right))))

;; Decoding
;; --------

(defn choose-branch [bit branch]
  (cond (= bit 0) (left-branch branch)
        (= bit 1) (right-branch branch)
        :else (throw (AssertionError. (str "Unrecognized bit " bit)))))

(defn decode [bits tree]
  (loop [result         []
         bits           bits
         current-branch tree]
    (if (empty? bits)
      result
      (let [next-branch (choose-branch (first bits) current-branch)]
        (if (leaf? next-branch)
          (recur (conj result (symbol-leaf next-branch))
                 (rest bits)
                 tree)
          (recur result
                 (rest bits)
                 next-branch))))))

;; Actual exercise
;; ---------------

(def sample-tree
  (make-code-tree (make-leaf 'A 4)
                  (make-code-tree (make-leaf 'B 2)
                                  (make-code-tree (make-leaf 'D 1)
                                                  (make-leaf 'C 1)))))

(def sample-message '(0 1 1 0 0 1 0 1 0 1 1 1 0))

(decode sample-message sample-tree)  ;; => [A D A B B C A]
