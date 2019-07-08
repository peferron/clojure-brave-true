; 1

(def movie (list (read-string "println") '(str "Pierre-Emile Ferron" ": " "Gattaca")))
(eval movie)

; 2

(def infixed '(1 + 3 * 4 - 5))

(def priorities {'+ 1
                 '- 1
                 '* 2
                 '/ 2})

(defn priority
  [item]
  (get priorities item 0))

(map priority infixed)

(defn max-priority
  [items]
  (apply max (map priority items)))

(max-priority infixed)

(defn simplify
  ([items]
   (simplify items (max-priority items)))
  
  ([items max-p]
   (let [[left-operand operator right-operand & remaining] items]
     (if (= (priority operator) max-p)
       (apply list (list operator left-operand right-operand) remaining)
       (apply list left-operand operator (simplify (cons right-operand remaining) max-p))))))

(simplify '(1 + 3))
(simplify '(1 + 3 * 4))
(simplify '(1 + 3 * 4 - 5))
(simplify '(1 + (* 3 4) - 5))
(simplify '((+ 1 (* 3 4)) - 5))

(defn infix
  [items]
  (if (second items)
    (recur (simplify items))
    (first items)))

(infix infixed)
(eval (infix infixed))
