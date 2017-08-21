(ns sicp.chapter2.exercise-24
  (:require [sicp.ascii-tree :refer [print-tree!]]))

(def tree (list 1 (list 2 (list 3 4))))

tree  ;; => (1 (2 (3 4)))

;;     .
;;   --^--
;;  /     \
;; 1       .
;;       --^--
;;      /     \
;;     2       .
;;            -^-
;;           /   \
;;          3     4

;; box-and-pointer is too much hassle in ascii, even with artist-mode.

;; (defn tree-map [f tree]
;;   (if (list? tree)
;;     (map (fn [subtree]
;;            (if (list? subtree)
;;              (tree-map f subtree)
;;              (f subtree)))
;;          tree)))
