(ns sicp.chapter2.exercise-10
  (:require [clojure.test :refer [is]]
            [sicp.chapter2.exercise-07 :as e07]))

(defn div-interval [i1 i2]
  {:pre [(pos? (* (e07/lower-bound i2) (e07/upper-bound i2)))]}
  (e07/div-interval i1 i2))

(is (thrown? Exception (div-interval (e07/make-interval 0 4)
                                     (e07/make-interval -2 2))))
