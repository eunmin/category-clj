(ns category-clj.monad)

(defprotocol Monad
  (flat-map [this ma f])
  (unit [this a]))

(defn fmap [mon fa f]
  (flat-map mon fa (fn [a]
                     (unit mon (f a)))))

(defn map2 [mon fa fb f]
  (flat-map mon fa (fn [a]
                     (fmap mon fb (fn [b]
                                    (f a b))))))
