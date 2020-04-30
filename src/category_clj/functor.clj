(ns category-clj.functor
  (:require [category-clj.data.either :as either]))

(defprotocol Functor
  (fmap [this fa f]))

(defn distribute [fun fab]
  [(fmap fun fab #(first %)) (fmap fun fab #(second %))])

(defn codistribute [fun {:keys [type] :as e}]
  (case type
    :left (fmap fun (:value e) either/left)
    :right (fmap fun (:value e) either/right)))

