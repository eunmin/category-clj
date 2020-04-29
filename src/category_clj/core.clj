(ns category-clj.core)


;; Foldable
(defn fold-right [as z f])
(defn fold-left [as z f])
(defn fold-map [as f mb])

;; Functor
(defn fmap [fa f])

;; Applicative
(defn ap [ff fa])

(defn pure [a])

;; Traverse

(defn traverse [fa f])

(defn sequence [fga])


;; Monad
(defn flat-map [ma f])

(defn join [mma])
