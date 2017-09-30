(ns sicp.chapter2.exercise-82
  (:require
   [sicp.chapter2.exercise-77 :as e77]
   [sicp.chapter2.exercise-78 :as e78]
   [sicp.chapter2.exercise-81 :as e81]))

(defn coerce-to [table curr-type]
  (fn [arg]
    (let [arg-type (e78/type-tag arg)]
      (if (= arg-type curr-type)
        arg
        (if-let [coercion-fn (e81/get-coercion table arg-type curr-type)]
          (coercion-fn arg)
          nil)))))

(defn apply-generic [dd-table c-table op & args]
  (let [type-tags (map e78/type-tag args)]
    (if-let [proc (e77/dd-get dd-table op type-tags)]
      (apply proc (map e78/contents args))
      (loop [remaining-types type-tags]
        (if-let [curr-type (first remaining-types)]
          (let [coerced-args (map (coerce-to c-table curr-type) args)]
            (if (every? coerced-args)
              (if-let [proc (e77/dd-get dd-table op (map e78/type-tag coerced-args))]
                (apply proc (map e78/contents coerced-args))
                (recur (rest remaining-types)))
              (recur (rest remaining-types))))
          (throw (ex-info "No method found."
                          {:op op :types type-tags})))))))

;; The problem is that this algorithm assumes that the generic process will
;; either have the given combination of argument types, or there will be an
;; equivalent process where all it's arguments are *the same type*.  This
;; shouldn't necessarily be true.
