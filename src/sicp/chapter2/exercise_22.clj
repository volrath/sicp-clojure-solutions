(ns sicp.chapter2.exercise-22)

;; it produces the answer list in reverse order because it starts with an empty
;; list (`nil`) and starts `cons`ing to it from the beginning of the given
;; `items` list, by putting each next element in `items` as the `car` of the new
;; `cons`:

;; items:  '(1 2 3)
;; answer: (cons (square 3) (cons (square 2) (cons (square 1) nil)))

;; --

;; Second version doesn't work either because...
;; answer: (cons (cons (cons nil (square 1)) (square 2)) (square 3))
;;      => (((nil 1) 2) 3)
