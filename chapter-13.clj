; 1

(defmulti full-moon-behavior
          (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :hogwarts-professor
  [were-creature]
  (str (:name were-creature) " will try to eat Harry Potter for dinner"))

(full-moon-behavior {:were-type :hogwarts-professor
                     :name "Lupin"})

; 2

(defprotocol WereCreature
  (full-moon-behavior [x]))

(defrecord WereSimmons [name]
  WereCreature
  (full-moon-behavior [x]
    (str name " will encourage people and sweat to the oldies")))

(full-moon-behavior (WereSimmons. "Bob"))

; 3 & 4

; Skipped
