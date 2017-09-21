(ns sicp.chapter2.exercise-70
  (:require [sicp.chapter2.exercise-68 :as e68]
            [sicp.chapter2.exercise-69 :as e69]))

(def alphabet
  '((A 2)
    (BOOM 1)
    (GET 2)
    (JOB 2)
    (NA 16)
    (SHA 3)
    (YIP 9)
    (WAH 1)))

;; Listen to the song: https://www.youtube.com/watch?v=ofEdoggWr-s

(def message '(GET A JOB
                   SHA NA NA NA NA NA NA NA NA
                   GET A JOB
                   SHA NA NA NA NA NA NA NA NA
                   WAH YIP YIP YIP YIP YIP YIP YIP YIP YIP
                   SHA BOOM))

(def huff-zip-size
  (count
   (e68/encode message (e69/generate-huffman-tree alphabet))))  ;; => 84

;; Fixed length code for eight-symbol alphabet requires 3 bits per symbol.
(def fixed-encoding-size (* 3 (count message)))  ;; => 108

;; Compression pctg
(float (/ (* huff-zip-size 100) fixed-encoding-size))  ;; => 77.777...
