; 1

(str "Hello" ", " "world" "!")
(vector 1 2 3 4)
(list 1 2 3 4)
(hash-map :a 1 :b 2)
(hash-set :a :b)

; 2

(defn inc-100
  "Adds 100"
  [x]
  (+ x 100))

(inc-100 123)

; 3

(defn dec-maker
  "Creates a decrementor"
  [dec-by]
  #(- % dec-by))

(def dec-100 (dec-maker 100))
(dec-100 123)

; 4

(defn mapset
  [f coll]
  (set (map f coll)))

(mapset inc [1 1 2 3])

; It looks like Clojure doesn't have a concept of lazy sets, so the
; implementation of `mapset` above hangs on the second line below:

(def rands (repeatedly #(rand-int 10)))
; (def negs (mapset - rands)) ; Hangs if uncommented

; One alternative is to use the `distinct` function, which returns a (lazy) seq
; instead of a set:

(defn mapset
  [f coll]
  (distinct (map f coll)))

(def negs (mapset - rands))
(take 10 negs)

; 5 & 6

(def asym-alien-body-parts [{:name "head" :size 3}
                            {:name "eye-1" :size 1}])

(defn matching-part
  [part index]
  (let [matching-part-suffix (str "-" index)
        matching-part-name (clojure.string/replace (:name part) #"-1$" matching-part-suffix)]
    (assoc part :name matching-part-name)))

(map #(matching-part % 2) asym-alien-body-parts)

(defn matching-parts
  [count part]
  (let [indexes (range 1 (inc count))]
    (set (map (partial matching-part part) indexes))))

(map (partial matching-parts 5) asym-alien-body-parts)

(defn symmetrize-body-parts
  [count asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (matching-parts count part)))
          []
          asym-body-parts))

(symmetrize-body-parts 5 asym-alien-body-parts)
