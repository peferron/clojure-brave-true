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

; 3

(defn dec-maker
  "Creates a decrementor"
  [dec-by]
  #(- % dec-by))

; 4

(defn mapset
  [f coll]
  (set (map f coll)))

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

; 5

