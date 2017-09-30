(ns sicp.chapter2.exercise-84
  (:require [sicp.chapter2.exercise-78 :as e78]
            [sicp.chapter2.exercise-77 :as e77]))

;; For the sake of simplicity, in this exercise we will assume both dynamic data
;; table and coercion table are global state variables.

(declare apply-generic dd-get get-coercion)

(defn get-highest-type [t1 t2]
  (loop [t t1]
    (if (= t t2)
      t2
      (if-let [upper-t (get-coercion t)]
        (recur upper-t)
        t1))))

(defn highest-in-tower [types]
  (loop [highest         (first types)
         remaining-types (rest types)]
    (if (empty? remaining-types)
      highest
      (recur (get-highest-type highest (first remaining-types))
             (rest remaining-types)))))

(defn coerce [arg type]
  (if (= (e78/type-tag arg) type)
    arg
    (coerce (apply-generic 'raise arg)
            type)))

(defn apply-generic [op & args]
  (let [type-tags (map e78/type-tag args)]
    (if-let [proc (dd-get op type-tags)]
      (apply proc (map e78/contents args))
      (if (> (count args) 1)
        (let [highest-type (highest-in-tower type-tags)]
          ;; First make sure if the op actually exist for the highest type.
          (if-let [proc (apply dd-get op (repeat (count args) highest-type))]
            (let [coerced-args (map #(coerce % highest-type) args)]
              (apply proc (map e78/contents coerced-args)))
            (throw (ex-info "No method found."
                            {:op op :types type-tags}))))
        (throw (ex-info "No method found."
                        {:op op :types type-tags}))))))
