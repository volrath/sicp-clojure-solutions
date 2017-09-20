(ns sicp.chapter2.exercise-68
  (:require [sicp.chapter2.exercise-33 :as e33]
            [sicp.chapter2.exercise-60 :as e60]
            [sicp.chapter2.exercise-67 :as e67]))

(defn element-of-tree? [symbol tree]
  (e60/element-of-set? symbol (e67/symbols tree)))

(defn encode-symbol [symbol tree]
  (if (or (not (element-of-tree? symbol tree))
          (empty? tree))
    (throw (AssertionError. (str "Couldn't find symbol " symbol " in tree.")))
    (loop [result '()
           branch tree]
      (cond (e67/leaf? branch) result

            (element-of-tree? symbol (e67/left-branch branch))
            (recur (e33/append result '(0)) (e67/left-branch branch))

            (element-of-tree? symbol (e67/right-branch branch))
            (recur (e33/append result '(1)) (e67/right-branch branch))))))

(defn encode [message tree]
  (if (empty? message)
    '()
    (e33/append (encode-symbol (first message) tree)
                (encode (rest message) tree))))

(assert (= (encode '(A D A B B C A) e67/sample-tree)
           e67/sample-message))
