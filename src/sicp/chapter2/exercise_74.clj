(ns sicp.chapter2.exercise-74
  (:require [sicp.chapter2.exercise-73 :as e73]))

;; a.

;; We assume that `personnel-file` is a file-name/string. We assume each
;; division uses their `personnel-file`'s file-name as the `type` key to get
;; their operations into the DD table.
;; Each division should implement two operators and installed them in DD:
;; 1. `iter-record` will give the employee set as a sequence/iterable/list.
;; 2. `get-employee-key` will receive an employee record/set and return its key.

(defn get-record [employee-key personnel-file]
  (let [iter-records     (e73/dd-get 'iter-records personnel-file)
        get-employee-key (e73/dd-get 'get-employee-key personnel-file)]
    (first (filter (fn [employee]
                     (= (get-employee-key employee)
                        employee-key))
                   (iter-records)))))

;; b.
;; Each division should implement their own `get-salary`. More over, each
;; division should make sure to add a `tag` to their employees records so they
;; can be easily distinguishable.

(declare division-tag)

(defn get-salary [employee-record]
  ((e73/dd-get 'get-salary (division-tag employee-record)) employee-record))

;; c.

(defn find-employee-record [employee-name division-files]
  (loop [files division-files]
    (if (empty? files)
      nil
      (let [file (first files)
            employee (get-record employee-name file)]
        (if employee
          employee
          (recur (rest files)))))))

;; The new company has to:
;; 1. provide a file with their personnel.
;; 2. give it a unique name
;; 3. implement their own `iter-record`, `get-employee-key`, and `get-salary`.
;; 4. implement their own `install-<division>-package` where they put their
;;    functions into the dd table
