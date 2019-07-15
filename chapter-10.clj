; 1

(def x (atom 0))
(swap! x inc)
(swap! x + 2)
(println @x)

; 2

(defn download-quote
  []
  (println "Start downloading quote")
  (let [quote (slurp "https://www.braveclojure.com/random-quote")]
    (println "Finished downloading quote")
    quote))

(defn words
  [quote]
  (->> quote
       clojure.string/split-lines
       first
       clojure.string/lower-case
       (re-seq #"\w+")))

(defn merge-quote
  [word-count quote]
  (merge-with + word-count (frequencies (words quote))))

(defn merge-quote-atom
  [word-count quote]
  (swap! word-count #(merge-quote % quote)))

(defn quote-word-count
  [n]
  (let [word-count (atom {})
        futures (repeatedly n #(future (merge-quote-atom word-count (download-quote))))]
    (dorun (map deref (doall futures)))
    @word-count))

(quote-word-count 5)

; 3

(def a (ref {:pots 2}))

(def b (ref {:cur 15
             :max 40}))

(defn heal
  [healer healed]
  (dosync
    (when (> (:pots healer) 0)
      (alter healer update :pots dec)
      (alter healed update :cur #(min (:max healed) (+ % 10))))))

(heal a b)
