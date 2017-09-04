(ns sicp.chapter2.exercise-53)

(defn memq [item x]
  (cond (empty? x)           false
        (= item (first x)) x
        :else              (memq item (rest x))))

(assert (and (= (memq 'apple '(x (apple sauce) y apple pear)) '(apple pear))
             (= (memq 'red '((red shoes) (blue socks))) false)
             (= (memq 'red '(red shoes blue socks)) '(red shoes blue socks))))
