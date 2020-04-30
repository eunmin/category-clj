(ns category-clj.data.either)

(defn left [value]
  {:type :left :value value})

(defn right [value]
  {:type :right :value value})
