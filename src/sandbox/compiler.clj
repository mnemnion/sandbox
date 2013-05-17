(ns sandbox.compiler)
;we need to take something like this

(def x "foo")

(defn a-funky ;except defsomethingelse 
	"a rule handler"
	[x] ;x is not actually a variable, it is a symbol, in this case predefined to reference "foo"
	(println  x))
;and turn it into this

(defn a-func
  "a rule handler"
	[rule cdr state] ;rule is :keyword, cdr is list or string, state is map,
					   ;note that x is absent
	(let [local-state state x x] ;we moved our literal x to the let statement 
	  (println x) 
	  local-state))

; so that we can repeatedly call a handler that uses the keyword to pull a-func
; (which would actually be gensymmed/anonymous) and call it, set! state to the return
; value, and call first on the cdr to get the next keyword.
;
; when the handler runs out of cdr, it returns the state. 
(def i-am-q "queue")
(def snippet (def-rule-fn (println (str state key) i-am-q)))

(def test-rule
     (def-rule-fn
       (println "State! " (str state))
       (println "Key! " (str key))
       (println "Root! " (str root))
       (println "Cdr! " (str cdr))))
                                   
(defmacro def-rule-fn [& body]  
        `(fn [~'key ~'state ~'root ~'cdr]
           (let [~'local-state ~'state]
           (println "Calling ...")
          
           ~@body
           (inc ~'local-state))))    
                      
           
#_(defn defhandler [fn-name lets & body]
          (build-fn-lets fn-name [name] (interleave lets lets) body ))
