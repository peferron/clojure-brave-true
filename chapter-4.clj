; Prerequisites

(def vamp-keys [:name :glitter-index])

(def suspects [{:name "Edward Cullen", :glitter-index 10}
               {:name "Bella Swan", :glitter-index 0}])

(defn glitter-filter
  [minimum-glitter records]
  (filter #(> (:glitter-index %) minimum-glitter) records))

; 1

(def glitter-filter-names
  (comp (partial map :name) glitter-filter))

(glitter-filter-names 3 suspects)

; 2

(def append conj) ; Wat

(append suspects {:name "Jacob Black" :glitter-index 3})

; 3

(def validations {:name #(and (string? %) (not-empty %))
                  :glitter-index number?})

(defn validate
  [validations record]
  (every? (fn [[keyword pred]] (pred (keyword record)))
          validations))

(validate validations (first suspects))
(validate validations {:name "Katherine"})
(validate validations {:name "" :glitter-index 0})

; 4

(defn serialize-record
  [record]
  (clojure.string/join "," (map #(% record) vamp-keys)))

(serialize-record (first suspects))

(defn serialize-records
  [records]
  (clojure.string/join "\n" (map serialize-record records)))

(serialize-records suspects)
