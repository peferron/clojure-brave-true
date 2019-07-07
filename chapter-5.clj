; 1

(defn attr
  [keyword]
  (comp keyword :attributes))

(def intelligence (attr :intelligence))
(intelligence {:attributes {:intelligence 10}})

; 2

(defn my-comp
  [& fs]
  (fn [arg] (reduce (fn [val f] (f val))
                    arg
                    (reverse fs))))

(def test-fn (my-comp inc (partial * 2)))
(test-fn 1)

; Alternative implementation with support for multiple arguments for the innermost function

(defn my-comp
  ([f] f)
  ([f & gs] #(f (apply (apply my-comp gs) %&))))

(def test-fn (my-comp inc *))
(test-fn 1 2)
