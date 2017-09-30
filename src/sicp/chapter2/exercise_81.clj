(ns sicp.chapter2.exercise-81
  (:require [sicp.chapter2.exercise-78 :as e78]
            [sicp.chapter2.exercise-77 :as e77 :refer [install-complex-rectangular-package
                                                       install-complex-polar-package
                                                       install-complex-package
                                                       install-rational-package
                                                       install-scheme-number-package]]))

(defn get-coercion [table t1 t2]
  (get-in table [t1 t2]))

(defn put-coercion [table t1 t2 coer-fn]
  (assoc-in table [t1 t2] coer-fn))

;; Actual exercise

;; a. If we put identity coercions in the table, we will get stack overflows.
;; b. Something needs to be fixed (c.) but not by adding identity coercions.

;; c.
(defn apply-generic [dd-table c-table op & args]
  (let [type-tags (map e78/type-tag args)]
    (if-let [proc (e77/dd-get dd-table op type-tags)]
      (apply proc (map e78/contents args))
      (if (= (count args) 2)
        (let [t1 (first type-tags)
              t2 (second type-tags)]
          (if (not= t1 t2)  ;; c. fix.
            (let [a1 (first args)
                  a2 (second args)
                  t1->t2 (get-coercion c-table t1 t2)
                  t2->t1 (get-coercion c-table t2 t1)]
              (cond t1->t2 (apply-generic dd-table c-table op (t1->t2 a1) a2)
                    t2->t1 (apply-generic dd-table c-table op a1 (t2->t1 a2))
                    :else (throw (ex-info "No method found."
                                          {:op op :types type-tags}))))
            (throw (ex-info "No method found."
                            {:op op :types type-tags}))))))))

;; Generic operations macro
;; (defmacro with-generic-operations [options & body]
;;   (let [default-opts {:packages      (list
;;                                       install-complex-rectangular-package
;;                                       install-complex-polar-package
;;                                       install-complex-package
;;                                       install-rational-package
;;                                       install-scheme-number-package)}
;;         options      (merge default-opts options)]
;;     `(let [~'dd-table      (reduce (fn [table# install-pkg-fn#]
;;                                      (install-pkg-fn# table#))
;;                                    {}
;;                                    '~(options :packages))
;;            ~'c-table       {}]
;;        ~@body)))
