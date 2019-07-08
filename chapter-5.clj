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

; 3

(defn my-assoc-in
  [m [k & ks] v]
  (if ks
    (assoc m k (my-assoc-in (get m k) ks v))
    (assoc m k v)))

(my-assoc-in {:a {:b 0 :c 1}} [:a :d] 2)
(my-assoc-in {} [:a :b] 0)

; 4

(update-in {:a {:b 0 :c 1}} [:a :c] inc)
(update-in {:a {:b 0 :c 1}} [:a :c] * 2)
(update-in {} [:a :b] (fn [_] 0))

; 5

(defn my-update-in
  [m [k & ks] f & args]
  (if ks
    (assoc m k (apply my-update-in (get m k) ks f args))
    (apply update m k f args)))

(my-update-in {:a {:b 0 :c 1}} [:a :c] inc)
(my-update-in {:a {:b 0 :c 1}} [:a :c] * 2)
(my-update-in {} [:a :b] (fn [_] 0))
