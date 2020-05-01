(ns category-clj.core
  (:require [category-clj.data.either :as either]
            [category-clj.functor :refer :all]
            [category-clj.foldable :refer :all]
            [category-clj.instance.list :as list]
            [category-clj.monad :refer [map2]]
            [category-clj.monoid :refer :all]))

;; Monoid

(def m (map-merge-monoid (map-merge-monoid int-addition-monoid)))
(def m1 {:o1 {:i1 1 :i2 2}})
(def m2 {:o1 {:i2 3}})
(op m m1 m2)


(def m (function-monoid int-addition-monoid))
(def m2 (op m count count))
(m2 "abc")


(def int-nil-monoid (product-monoid int-addition-monoid nil-monoid))
(op int-nil-monoid [1 nil] [2 3])

;; Foldable

(fold-left list/instance [1 2 3] 100 +)

(fold-map list/instance [1 2 3] (fn [x]
                                  (+ x 10))
          int-addition-monoid)

(concatenate list/instance [1 2 3] int-addition-monoid)

(to-list list/instance [1 2 3])

(def m (product-monoid int-addition-monoid int-addition-monoid))
(def p (fold-map list/instance [1 2 3 4] (fn [a] [1 a]) m))
(/ (first p) (double (second p)))


;; Functor

(distribute list/instance [["*" 1] ["?" 2] ["?" 3] ["*" 4]])

(codistribute list/instance (either/right [1 2 3]))


;; Monad

(map2 list/instance [1] [2] (fn [a b] (str a b)))
