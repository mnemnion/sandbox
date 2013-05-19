# instaparse.aacc

Actually A Compiler Compiler. A backend to Instaparse that lets you do arbitrary things to a parse tree. 

yacc, and instaparse, are not compiler compilers. yacc is a parser compiler, and hence a parser parser; instaparse is in that family, but Clojurian, and delectably lexer free.

aacc extends that flexibility, allowing a single-pass, rule-driven walk through a parse tree.

##Usage

aacc takes a map of keywords to functions defined using the def-rule-fn macro. The keywords correspond to instaparse rule names; __add__ optionally, a map of literal tokens to rules may also be provided. __add__

aac is called like this:

```clojure
(aacc state tree)
;or
(aacc state tree rule-map)
;or ---tba---
(aacc state tree rule-map token-map)
```

'state' is a map, which is initialized with the rule map as the value of a **:rule-map** key. **:token-map** may be added to state as well; the 3 and 4 argument forms push the maps into state before beginning the seq.

The variable order allows a convenient definition for a compiler function:

```clojure
(def compiler (partial aacc {:rule-map rule-map}))
(compiler some-instaparse-tree)
```

Which, when called on a tree, compiles it. Whether this is true compilation or interpretation depends on whether the focus is on side effects or on the contents of state.

##Behavior

aacc recursively walks the parse tree, repeatedly calling the rule functions. Rule functions have convenient access to four magic variables: **state**, **rule-key**, **root**, and **seq-tree**. the **rule-key** is the keyword which called the rule, **root** is the entire tree, and **seq-tree** is a sequence of the remaining tree to be walked. 

**(rest seq-tree)** will give you the next node on the tree, as expected. 

All rule functions are expected to return state, in a useful fashion.

state contains everything but the seq-tree, which ensures that aacc will exit unless a subrule contains an infinite loop. this means the value of **root**, **rule-map** and **token-map** may be dynamically changed by modifying the bindings of **:root-tree**, **:rule-map** or **:token-map** within the state map. 

aacc will exit immediately if the returned state map contains a value for the keyword **:stop**. **:error** is probably a good place to put things that go wrong, and **:warning** might be a nice location for warnings. 

**:stop**, **:root-tree**, **:rule-map**, **:token-map**, and **:error** are the only magic values in state. Modifying the value of **:root-tree** will not change the underlying tree-seq, which is baked at compile time and will walk the entire tree exactly once. Changing the mapping of **:root-tree** modifies any rule-based use of **root** subsequent to the change, but will not affect aacc's function directly. 

If the rule map does not contain a particular keyword, the default rule, **instaparse.aacc/default-rule**, is used. It returns state, doing nothing further. Literal tokens that are not matched by the token map call **instaparse.aacc/default-token-rule**. Both of these may be over-ridden if necessary, in the following fashion:

```clojure
(alter-var-root #'instaparse.aacc/default-rule (constantly new-rule))
```
Where new-rule should be created with the def-rule-fn macro or provide the same magic variables. 

## License

Copyright © 2013 Sam Putman.

Distributed under the BSD 2 Clause License. 
