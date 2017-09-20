(ns sicp.chapter2.exercise-72)

;; Assumption: Relative frequencies for symbols: 1, 2, 4, ..., 2^(n-1)

;; encode-symbol:
;; element-of-tree: O (n/2)
;; tree size in general is n-1
;; ((n-1)/2) * n/2 => (n/2 - 1/2) * n/2 => n^2/4 - n/2, => O(n^2)
