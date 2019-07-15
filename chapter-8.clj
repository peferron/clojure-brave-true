; 1

; For simplicity, use built-in validation function and message

(defn validate
  [to-validate]
  (if (:valid to-validate)
    {}
    {:oops "Invalid!"}))

(defmacro if-valid
  [to-validate errors-name & then-else]
  `(let [~errors-name (validate ~to-validate)]
     (if (empty? ~errors-name)
       ~@then-else)))

(macroexpand '(if-valid to-validate errors then else))

(defn test-if-valid
  [to-validate]
  (if-valid to-validate errors
            (println "Success")
            (println "Failure: " errors)))

(test-if-valid {:valid true})
(test-if-valid {})

(defmacro when-valid
  [to-validate & do]
  `(when (empty? (validate ~to-validate))
     ~@do))

(macroexpand '(when-valid to-validate do))

(defn test-when-valid
  [to-validate]
  (when-valid to-validate
              (println "Success")
              (println "Really!")
              "Return something"))

(test-when-valid {:valid true})
(test-when-valid {})

; 2

(defmacro my-or
  ([] nil)
  ([x] x)
  ([x & next] `(let [x# ~x]
                 (if x#
                   x#
                   (my-or ~@next)))))

(macroexpand '(or 1 2 3))

(defn my-true
  []
  (println "True :)")
  true)

(defn my-false
  []
  (println "False :(")
  false)

(or (my-false) (my-true) (my-true))
