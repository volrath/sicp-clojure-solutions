(ns sicp.chapter1.exercise-42)

(defn compose [f g]
  (fn [x]
    (f (g x))))

((compose #(* % %) inc) 6)
