(ns category-clj.foldable
  (:require [category-clj.monoid :as monoid]))

(defprotocol Foldable
  (fold-right [this as z f])
  (fold-left [this as z f])
  (fold-map [this as f mb]))

(defn concatenate [foldable as m]
  (fold-left foldable as (monoid/zero m) (partial monoid/op m)))

(defn to-list [foldable as]
  (fold-right foldable as '() (fn [b a]
                                (cons a b))))
