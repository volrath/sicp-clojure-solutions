(ns sicp.exercise-12)

(defn pascal-item [row col]
  {:pre  [(> row 0) (> col 0) (>= row col)]
   :post [(pos? %)]}
  (if (or (= 1 col) (= row col))
    1
    (let [prev-row (dec row)
          prev-col (dec col)]
      (+ (pascal-item prev-row prev-col)
         (pascal-item prev-row col)))))

(pascal-item 5 3)
