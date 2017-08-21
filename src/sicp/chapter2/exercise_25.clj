(ns sicp.chapter2.exercise-25)

(def car first)
(def cdr rest)

(car (cdr (car (cdr (cdr '(1 2 (5 7) 9))))))

(car (car '((7))))

(car
 (cdr
  (car
   (cdr
    (car
     (cdr
      (car
       (cdr
        (car
         (cdr (car (cdr '(1 (2 (3 (4 (5 (6 7))))))))))))))))))
