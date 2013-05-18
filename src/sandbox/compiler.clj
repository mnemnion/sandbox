(ns sandbox.compiler)

; so that we can repeatedly call a handler that uses the keyword to pull a-func
; (which would actually be gensymmed/anonymous) and call it, set! state to the return
; value, and call first on the cdr to get the next keyword.
;
; when the handler runs out of cdr, it returns the state. 
(def i-am-q "queue")
(def snippet (def-rule-fn (println (str state key) i-am-q)))

(def test-rule
     (def-rule-fn
       (println "Key! " (str key))
       (println "State! " (str state))
       (println "Root! " (str root))
       (println "Cdr! " (str cdr))))
                                   
(defmacro def-rule-fn [& body]  
        `(fn [~'key ~'state ~'root ~'cdr]
           (let [~'local-state ~'state]
           (println "Calling ...")    
           ~@body
           (def ~'state (inc ~'local-state)))))
                      
           
#_(defn defhandler [fn-name lets & body]
          (build-fn-lets fn-name [name] (interleave lets lets) body ))
