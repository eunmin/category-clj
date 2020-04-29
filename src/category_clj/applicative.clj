(ns category-clj.applicative
  (:require [category-clj.functor :refer [Functor fmap]]))

(defprotocol Applicative
  (map2 [this fa fb f])
  (unit [this a]))

(defn applicative-fmap [app fa f]
  (map2 app fa (unit app nil) (fn [a _] (f a))))


(def a (reify
         Applicative
         (map2 [this fa fb f] 1)
         (unit [this a] 2)
         Functor
         (fmap [this fa f] (applicative-fmap this fa f))))

(defprotocol Traverse
  (traverse [this fa f]))

(defn sequence [trav fma]
  (traverse trav fma identity))
