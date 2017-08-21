(ns sicp.chapter2.exercise-29)

(defn make-mobile [left right]
  (list left right))

(defn make-branch [length structure]
  (list length structure))

;; a.
(defn left-branch [mobile]
  (first mobile))

(defn right-branch [mobile]
  (second mobile))

(defn branch-length [branch]
  (first branch))

(defn branch-structure [branch]
  (second branch))

;; b.
(defn total-weight [mobile]
  (if (number? mobile)
    mobile
    (+ (total-weight (branch-structure (left-branch mobile)))
       (total-weight (branch-structure (right-branch mobile))))))

;; c.
(defn torque [branch]
  (* (branch-length branch)
     (total-weight (branch-structure branch))))

(defn balanced? [mobile]
  (if (number? mobile)
    true
    (let [lb (left-branch mobile)
          rb (right-branch mobile)]
      (and (balanced? (branch-structure lb))
           (balanced? (branch-structure rb))
           (= (torque lb) (torque rb))))))

;; d.

;; If I would've used Scheme's car and cdr, I'd have had to change
;; `(car (cdr elem))` to get the second element of the list, by just
;; `(cdr elem)`, since `elem` would be a pair, not a list

;; test:
;;                   |
;;                 3 | 3
;;                -------             <- 2
;;                |     |
;;                6   2 | 2
;;                    -----           <- 1
;;                    |   |
;;                    3   3

(def m1
  (make-mobile (make-branch 2 3)
               (make-branch 2 3)))
(def m2
  (make-mobile (make-branch 3 6)
               (make-branch 3 m1)))

(assert (balanced? m2))
