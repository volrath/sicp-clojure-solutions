(ns sicp.chapter2.exercise-66
  (:require [sicp.chapter2.exercise-63 :as e63]))

(defn lookup [given-key set-of-records]
  (if (nil? set-of-records)
    false
    (let [current-entry (e63/entry set-of-records)
          current-key (:key current-entry)]
      (cond (= given-key current-key) current-entry
            (< given-key current-key) (lookup given-key (e63/left-branch set-of-records))
            (> given-key current-key) (lookup given-key (e63/right-branch set-of-records))))))
