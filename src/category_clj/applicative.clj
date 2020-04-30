(ns category-clj.applicative
  (:require [category-clj.functor :refer [Functor fmap]]))

(defprotocol Applicative
  (map2 [this fa fb f])
  (unit [this a]))

(def a-functor {:fmap (fn [this fa f]
                        1)})

(def nil-applicative
  (reify
    Applicative
    (map2 [this fa fb f])
    (unit [this a])
    Functor
    (fmap [this fa f])))

#_(extend-protocol Applicative
  java.lang.Object
  (fmap [this fa f]
    (map2 this fa (unit this nil) (fn [a _] (f a)))))

(defrecord TypeApplicative []
  Applicative
  (map2 [this fa fb f] 1)
  (unit [this a] 2))

#_(def a (AApplicative.))


#_(satisfies? Functor a)



(defprotocol Traverse
  (traverse [this fa f]))

(defn sequence [trav fma]
  (traverse trav fma identity))


#_(fmap a 1 1)


(defmulti map2 (fn [this fa fb f] this))

(defmulti unit (fn [this a]))


