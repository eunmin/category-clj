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

(def list-monoid
  (reify Monoid
    (op [this a1 a2]
      (concat a1 a2))
    (zero [this]
      '())))

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


(defprotocol Foldable
  (fold-right [this as z f])
  (fold-left [this as z f])
  (fold-map [this as f mb]))

(defn concatenate [foldable as m]
  (fold-left foldable as (zero m) (partial op m)))

(defn to-list [foldable as]
  (fold-right foldable as '() (fn [b a]
                                (cons a b))))

(defn foldl [f val coll]
  (if (empty? coll) val
      (foldl f (f val (first coll)) (rest coll))))

(defn foldr [f val coll]
  (if (empty? coll) val
      (f (foldr f val (rest coll)) (first coll))))

(def list-foldable
  (reify Foldable
    (fold-right [this as z f]
      (foldr f z as))
    (fold-left [this as z f]
      (foldl f z as))
    (fold-map [this as f mb]
      (reduce (fn [b a]
                (op mb b (f a)))
              (zero mb)
              as))))

(fold-left list-foldable [1 2 3] 100 +)
(fold-map list-foldable [1 2 3] (fn [x]
                                  (+ x 10))
          int-addition-monoid)

(concatenate list-foldable [1 2 3] int-addition-monoid)

(to-list list-foldable [1 2 3])

(defn product-monoid [ma mb]
  (reify Monoid
    (op [this [xa xb] [ya yb]]
      [(op ma xa ya) (op mb xb yb)])
    (zero [this]
      [(zero ma) (zero mb)])))

(def int-nil-monoid (product-monoid int-addition-monoid nil-monoid))

(op int-nil-monoid [1 nil] [2 3])

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

(def m (map-merge-monoid (map-merge-monoid int-addition-monoid)))

(def m1 {:o1 {:i1 1 :i2 2}})
(def m2 {:o1 {:i2 3}})
(op m m1 m2)

(defn function-monoid [b]
  (reify Monoid
    (op [this f g]
      (fn [a]
        (op b (f a) (g a))))
    (zero [this]
      (fn [a]
        (zero b)))))

(def m (function-monoid int-addition-monoid))
(def m2 (op m count count))

(m2 "abc")

(def m (product-monoid int-addition-monoid int-addition-monoid))
(def p (fold-map list-foldable [1 2 3 4] (fn [a] [1 a]) m))

(/ (first p) (double (second p)))
