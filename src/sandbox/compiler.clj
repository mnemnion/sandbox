(ns sandbox.compiler
    (:require [sandbox.core :as s]))


(defmacro def-rule-fn [& body]  
        `(fn [~'rule-key ~'state ~'root ~'seq-tree]
           #_(println "Calling ..." (str ~'rule-key))    
           ~@body))
           
(defn- e-tree-seq 
  "tree-seqs enlive trees, at least instaparse ones"
  [e-tree]
  (tree-seq (comp seq :content) :content e-tree))
  
                 
(def ^:private test-rule
     (def-rule-fn
       (println "Keyword! " (str rule-key))
       (println "State! " (str (:count state)))
       (println "Root! " (str (:tag root)))
       (println "Contents! " (apply str (:content (first seq-tree))))                              
       (println "Seq Tree! " (apply str seq-tree))
                          (assoc state :count (inc (:count state)))))
                       

                                                                
        
(def ^:private test-rule-map
     {:tree test-rule
      :node test-rule
      :leaf test-rule})

(def default-rule
      (def-rule-fn
        state))

(def literal-token-rule default-rule
     #_(def-rule-fn
       (println "Executing Literal Token Rule ")
       (println "Literal Token Contains \"" (apply str (first seq-tree)) "\"")
                                                            (assoc state :count (inc (:count state))) ))
                                                                                             
; to replace default-rule, 
; or literal-token-rule, 
; (alter-var-root #'instaparse.aacc/default-rule (constantly new-rule))
 
(defn- retrieve-rule
  [seq-tree rule-map]
  (let [rule-fn ((:tag (first seq-tree)) rule-map)] 
       (if (fn? rule-fn)
           rule-fn
           default-rule)))
                             
(defn aacc-looper
   [tree seq-tree state] 
   (if (coll? (:content (first seq-tree)))
       (recur tree (rest seq-tree) ((retrieve-rule seq-tree (:rule-map state)) (:tag (first seq-tree)) state tree seq-tree))
       (if (seq seq-tree)
           (recur tree (rest seq-tree) (literal-token-rule :aaac-key-for-string state tree seq-tree))
             state)))

(defn aacc
  "actually a compiler compiler:
   
   tree is instaparse-enlive format \n
   state initializes the compiler \n
   if rule-map is not provided state 
   must contain :rule-map.
   
   returns state."
  ([tree state]
   (aacc-looper tree (e-tree-seq tree) state))
  ([tree state rule-map]
   (aacc-looper tree (e-tree-seq tree) (assoc state :rule-map rule-map))))
                     
        