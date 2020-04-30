(ns category-clj.monad)

(defprotocol Monad
  (flat-map [this ma f])
  (unit [this a]))
