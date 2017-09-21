(ns sicp.chapter2.exercise-75)

(defn make-from-mag-ang [r a]
  (fn [op]
    (case op
      real-part (* r (Math/cos a))
      imag-part (* r (Math/sin a))
      magnitude r
      angle     a)))

(let [z (make-from-mag-ang 1 2)]
  (assert (and (= (z 'magnitude) 1)
               (= (z 'angle) 2))))
