(ns sicp.chapter2.exercise-71
  (:require [clojure.math.numeric-tower :refer [expt]]
            [rhizome.viz :refer [view-tree]]
            [sicp.chapter2.exercise-67 :as e67]
            [sicp.chapter2.exercise-69 :as e69]))

(defn letter-number [number]
  (-> number
      (+ 65)
      char
      str
      symbol))

(defn gen-pairs
  "Generates N pairs of LETTER / WEIGHT.
  LETTERs start with A.
  The WEIGHT of each LETTER is 1, 2, 4, ..., 2^(n-1)."
  [n]
  (map (fn [i]
         (list (letter-number i)
               (expt 2 i)))
       (range n)))

(assert (= (gen-pairs 5)
           '((A 1) (B 2) (C 4) (D 8) (E 16))))

;; Data visualization

(def branch? (comp not e67/leaf?))

(defn children [tree]
  (list (e67/left-branch tree) (e67/right-branch tree)))

(defn node-descriptor [tree]
  (let [symbols (e67/symbols tree)
        weight  (e67/weight tree)]
    {:label (str (clojure.string/join " " symbols) " [" weight "]")}))

(defn sketch-tree [n]
  (let [tree (e69/generate-huffman-tree (gen-pairs n))]
    (view-tree branch? children tree
               :node->descriptor node-descriptor)))

(sketch-tree 5)
;; 1 bit for the most frequent symbol,
;; n-1 bits for the least frequent one.
