(ns category-clj.monoid)

(defprotocol Monoid
  (op [this a1 a2])
  (zero [this]))

(def string-monoid
  (reify Monoid
    (op [this a1 a2]
      (str a1 a2))
    (zero [this]
      "")))

(def int-addition-monoid
  (reify Monoid
    (op [this a1 a2]
      (+ a1 a2))
    (zero [this]
      0)))

(def int-multiplication-monoid
  (reify Monoid
    (op [this a1 a2]
      (* a1 a2))
    (zero [this]
      1)))

(def boolean-or-monoid
  (reify Monoid
    (op [this a1 a2]
      (or a1 a2))
    (zero [this]
      false)))

(def boolean-and-monoid
  (reify Monoid
    (op [this a1 a2]
      (and a1 a2))
    (zero [this]
      true)))

(def nil-monoid
  (reify Monoid
    (op [this a1 a2]
      (or a1 a2))
    (zero [this]
      nil)))

(def endo-monoid
  (reify Monoid
    (op [this f g]
      (comp f g))
    (zero [this]
      (fn [a] a))))

(defn product-monoid [ma mb]
  (reify Monoid
    (op [this [xa xb] [ya yb]]
      [(op ma xa ya) (op mb xb yb)])
    (zero [this]
      [(zero ma) (zero mb)])))

(defn map-merge-monoid [v]
  (reify Monoid
    (zero [this]
      {})
    (op [this a b]
      (reduce
       (fn [acc k]
         (assoc acc k (op v (get a k (zero v)) (get b k (zero v)))))
       (zero this)
       (set (concat (keys a) (keys b)))))))

(defn function-monoid [b]
  (reify Monoid
    (op [this f g]
      (fn [a]
        (op b (f a) (g a))))
    (zero [this]
      (fn [a]
        (zero b)))))
