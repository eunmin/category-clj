(ns category-clj.instance.list
  (:require [category-clj.functor :refer [Functor] :as functor]
            [category-clj.monoid :refer [Monoid] :as monoid]
            [category-clj.monad :refer [Monad] :as monad]
            [category-clj.foldable :refer [Foldable] :as foldable]))

(defn foldl [f val coll]
  (if (empty? coll) val
      (foldl f (f val (first coll)) (rest coll))))

(defn foldr [f val coll]
  (if (empty? coll) val
      (f (foldr f val (rest coll)) (first coll))))

(def instance
  (reify
    Monoid
    (op [this a1 a2]
      (concat a1 a2))
    (zero [this]
      '())

    Foldable
    (fold-right [this as z f]
      (foldr f z as))
    (fold-left [this as z f]
      (foldl f z as))
    (fold-map [this as f mb]
      (reduce (fn [b a]
                (monoid/op mb b (f a)))
              (monoid/zero mb)
              as))
    
    Functor
    (fmap [this fa f]
      (map f fa))

    Monad
    (flat-map [this ma f]
      (flatten (map f ma)))

    (unit [this a]
      (list a))))
