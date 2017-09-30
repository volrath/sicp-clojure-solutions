(ns sicp.chapter2.exercise-85
  (:require [sicp.chapter2.exercise-78 :as e78]
            [sicp.chapter2.exercise-84 :as e84]))

;; Again, we're going to take dynamic data tables and coercion tables
;; implicitly.

(declare dd-get real-part numer)

(defn dd-put [& args]
  ;; placeholder
  )

(dd-put 'project '(complex)
        (fn [z] (real-part z)))

(dd-put 'project '(real) int)

(dd-put 'project '(rational)
        (fn [r] (numer r)))

(defn drop [datum]
  (loop [d datum]
    (if-let [proc (dd-get 'project (e78/type-tag d))]
      (let [projected-datum (proc d)
            raise (dd-get 'raise (e78/type-tag projected-datum))]
        (if (= (raise projected-datum) d)
          (recur projected-datum)
          d))
      d)))

(defn apply-generic [op & args]
  (let [type-tags (map e78/type-tag args)]
    (if-let [proc (dd-get op type-tags)]
      (drop (apply proc (map e78/contents args)))
      (if (> (count args) 1)
        (let [highest-type (e84/highest-in-tower type-tags)]
          ;; First make sure if the op actually exist for the highest type.
          (if-let [proc (apply dd-get op (repeat (count args) highest-type))]
            (let [coerced-args (map #(e84/coerce % highest-type) args)]
              (drop (apply proc (map e78/contents coerced-args))))
            (throw (ex-info "No method found."
                            {:op op :types type-tags}))))
        (throw (ex-info "No method found."
                        {:op op :types type-tags}))))))
