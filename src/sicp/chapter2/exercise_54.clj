(ns sicp.chapter2.exercise-54)

(defn equal? [l1 l2]
  (let [really-equal? (fn [e1 e2]
                        (or (and (symbol? e1) (symbol? e2) (= e1 e2))
                            (and (list? e1) (list? e2) (equal? e1 e2))))]
    (if (and (empty? l1) (empty? l2))
      true
      (and (really-equal? (first l1) (first l2))
           (equal? (rest l1) (rest l2))))))

(assert (and (equal? '(x (apple sauce) y apple pear)
                     '(x (apple sauce) y apple pear))
             (not (equal? '(this is a list)
                          '(this (is a) list)))))
