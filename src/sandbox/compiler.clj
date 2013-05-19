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
       #_(println "Root! " (str (:tag root)))
       #_(println "Contents! " (apply str (:content (first seq-tree))))
       #_(println "First of Seq-Tree!" (apply str (first seq-tree)))
       (println "Seq Tree! " (apply str seq-tree))
       (when (string? (first (rest seq-tree)))
             (println "we got a token coming up"))
       (if (= rule-key :tree)
           (assoc state :count 1)
           (assoc state :count (inc (:count state))))))  


(def ^:private test-token-rule 
     (def-rule-fn
       (println "Executing Literal Token Rule ")
       (println "Literal Token Contains \"" (apply str (first seq-tree)) "\"")
                                              
                                                            (assoc state :count (inc (:count state))) ))

(def ^:private test-rule-map
     {:tree test-rule
      :node test-rule
      :leaf test-rule})
      
(def ^:private test-token-map
      {"(" test-token-rule
       ")" test-token-rule })
       



(def default-rule
      (def-rule-fn
       (println "default rule reached")
        state))

(def default-token-rule
      (def-rule-fn
       (println "default token rule reached")
        state))

(def default-rule-map
     {:aac-default-rule default-rule})

(def default-token-map
     {:aac-default-token-rule default-token-rule})

; to replace default-rule, 
; or literal-token-rule, 
; (alter-var-root #'instaparse.aacc/default-rule (constantly new-rule))
 
(defn- retrieve-rule
  [seq-tree rule-map]
  (let [rule-fn ((:tag (first seq-tree)) rule-map)] 
       (if (not (false? rule-fn))
           rule-fn
           default-rule)))
           
(defn- retrieve-token-rule
  [seq-tree rule-map]
  #_(let [rule-fn (rule-map (first seq-tree))] 
       (if (not (false? rule-fn))
           rule-fn
           default-token-rule)) default-token-rule)
                             
(defn aacc-looper
   [state seq-tree] 
   (let [tree (:root-tree state)]
   (if (not (:stop state))
       (if (coll? (:content (first seq-tree)))
         (recur ((retrieve-rule seq-tree (:rule-map state)) (:tag (first seq-tree)) state tree seq-tree) (rest seq-tree)) 
         (if (seq seq-tree)
           (recur ((retrieve-token-rule seq-tree (:token-rule-map state)) (first seq-tree)  state tree seq-tree) (rest seq-tree))
           state))
        state)))

(defn aacc
  "actually a compiler compiler:
   
   tree is instaparse-enlive format \n
   state initializes the compiler \n
   if rule-map is not provided state 
   should contain :rule-map, or no behavior
   will result.
   
   returns state."
  ([state tree]
    (aacc-looper (assoc state :root-tree tree ) (e-tree-seq tree) ))              
  ([state tree rule-map]
   (aacc-looper (assoc state :rule-map rule-map :root-tree tree) (e-tree-seq tree))))
                     
        