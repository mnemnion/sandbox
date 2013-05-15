(ns sandbox.core
  (:use     [clojure.repl]
   :use [monger.collection :only [insert insert-batch find-one-as-map find-maps]])
  (:require [monger.core :as mg]
            [rhizome.viz :refer [view-tree]]
            [me.raynes.conch :refer [programs with-programs let-programs]]
            [clojure.string :refer [split upper-case]]
            [instaparse.core :refer [parser parse parses]])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))

(defn fire-up-mongo []
  (do (programs mongod)
      (mongod)
      (println "I just made a mongo")
      (mg/connect!)
      (mg/set-db! (mg/get-db "mongo-test"))
      (find-one-as-map "documents" {:first_name "John"})))
 
 (defn make-alternates [foo] 
   (str "(" "'" (upper-case foo ) "'" "|" "'" foo "'" ")"))
 
 (defn caseify [token]
   "produce a case insensitive token for ANTLR"
   (println (apply str 
                   (map make-alternates 
                        (rest (split token #""))))))
 
 (def json-parse
      "instaparser for JSON"
      (parser (slurp "json.grammar") :output-format :enlive))
              
(def make-tree
     "simple tree parser"
     (parser "tree: node* 
              node: leaf | <'('> node (<'('> node <')'>)* node* <')'> 
              leaf: #'a+'
              "))
              
(def a-tree (make-tree "aaaa(aaaa(aaa(aa)aa)aaa)aaaaa"))  
              
(defn- acceptable? [node]
       (not (keyword? node)))
              
(defn- seq-for-labels [tree]
            (filter acceptable? tree))
              
(defn tree-viz 
    "visualize instaparse hiccup output as a rhizome graph" 
    [tree]
    (view-tree sequential? seq-for-labels tree 
               :node->descriptor (fn [n] {:label (if (vector? n) 
                                                     (first n) 
                                                     (when (string? n) n ))})))

(tree-viz a-tree)


