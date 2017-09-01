(ns sicp.chapter2.exercise-45
  (:require [sicp.chapter2.exercise-51 :as e51]))

(defn split [main-tr recur-tr]
  (fn splitted [painter n]
    (if (zero? n)
      painter
      (let [smaller (splitted painter (dec n))]
        (main-tr painter (recur-tr smaller smaller))))))

(def right-split (split e51/beside e51/below))
(def up-split (split e51/below e51/beside))
