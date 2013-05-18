(ns sandbox.compiler
    (:require [sandbox.core :as s]))

; so that we can repeatedly call a handler that uses the keyword to pull a-func
; (which would actually be gensymmed/anonymous) and call it, set! state to the return
; value, and call first on the cdr to get the next keyword.
;
; when the handler runs out of cdr, it returns the state. 

(def i-am-q "queue")

(def snippet (def-rule-fn (println (str state key) i-am-q)))
                                   
(defmacro def-rule-fn [& body]  
        `(fn [~'rule-key ~'state ~'root ~'cdr]
           (println "Calling ..." (str ~'rule-key))    
           ~@body))
           
(defn- e-tree-seq 
  "tree-seqs enlive trees, at least instaparse ones"
  [e-tree]
  (tree-seq (comp seq :content) :content e-tree))
  
                 
(def test-rule
     (def-rule-fn
       (println "Keyword! " (str rule-key))
       (println "State! " (str state))
       (println "Root! " (str (:tag root)))
       (println "Cdr! " (str (first cdr)))
                          (inc state)))
                             
(def test-rule-map
     {:tree test-rule
      :node test-rule
      :leaf test-rule})
                             
(defn aacc-looper

   [tree seq-tree rule-map state & args] ;ignore my nil side-effect from println etc
   (if (< state 3)
       (recur tree (rest seq-tree) rule-map (inc state) (println (str (first seq-tree))))
       (println (str "sez: " state))))

#_(((:tag (first seq-tree)) rule-map) (:tag (first seq-tree)) state tree seq-tree)

(defn aacc
  "actually a compiler compiler
   takes an instaparse enlive tree
   and a rule-map with :rule keys and
   def-rule-fn vals"
  [tree rule-map state]
  (aacc-looper tree (e-tree-seq tree) rule-map state))
                     
        