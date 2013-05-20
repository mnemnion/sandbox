# instaparse.aacc

Actually A Compiler Compiler. A backend to Instaparse that lets you do arbitrary things with a parse tree. 

yacc, and instaparse, are not compiler compilers. yacc is a parser compiler, and hence a parser parser; instaparse is in that family, but Clojurian, and delectably lexer free.

aacc extends that flexibility, allowing a single-pass, rule-driven walk through a parse tree.

aacc is also a bit of a pun:

```clojure
{:a a :c c}
```

##Usage

aacc takes a map of keywords to functions defined using the def-rule-fn macro. The keywords correspond to instaparse rule names;  optionally, a map of literal tokens to rules may also be provided.

aac is called like this:

```clojure
(aacc state tree)
;or
(aacc state tree rule-map)
;or
(aacc state tree rule-map token-map)
```

'state' is a map, which is initialized with the rule map as the value of a **:rule-map** key. **:token-map** may be added to state as well; the 3 and 4 argument forms push the maps into state before beginning the seq.

The variable order allows a convenient definition for a compiler function:

```clojure
(def compiler (partial aacc {:rule-map rule-map}))
(compiler some-instaparse-tree)
```

Which, when called on a tree, compiles it. Whether this is true compilation or interpretation depends on whether the focus is on side effects or on the contents of state, which could have, for example, an **:executable-binary** keyword, a **:clojure-program**, or anything else. 

##Behavior

aacc **recur**sively walks the parse tree, repeatedly calling the rule functions. Rule functions have convenient access to four magic variables: **state**, **rule-key**, **root**, and **seq-tree**. the **rule-key** is the keyword which called the rule, **root** is the entire tree, and **seq-tree** is a sequence of the remaining tree to be walked. 

```clojure
(first (rest seq-tree) 
```
will give you the next node on the tree, as expected. 

All rule functions are expected to return state, in a useful fashion.

state contains everything but the seq-tree, which ensures that aacc will exit unless a subrule contains an infinite loop. this means the value of **root**, **rule-map** and **token-map** may be dynamically changed by modifying the bindings of **:root-tree**, **:rule-map** or **:token-map** within the state map. 

aacc will exit immediately if the returned state map contains a value for the keyword **:stop**. **:error** is probably a good place to put things that go wrong, and **:warning** might be a nice location for warnings. 

**:stop**, **:root-tree**, **:rule-map**, **:token-map**, and **:error** are the only magic values in state. Modifying the value of **:root-tree** will not change the underlying tree-seq, which is baked at compile time and will walk the entire tree exactly once. Changing the mapping of **:root-tree** modifies any rule-based use of **root** subsequent to the change, but will not affect aacc's function directly. 

There are no magic keywords in the **rule-map** or **token-map**. These are the namespae of the language you're parsing, and it is hygenic: anything instaparse will accept as a rule name or literal token may be specified. 

If the rule map does not contain a particular keyword, the default rule, **instaparse.aacc/default-rule**, is used. It returns state, doing nothing further. Literal tokens that are not matched by the token map call **instaparse.aacc/default-token-rule**. Both of these may be over-ridden if necessary, in the following fashion:

```clojure
(alter-var-root #'instaparse.aacc/default-rule (constantly new-rule))
```
Where new-rule should be created with the def-rule-fn macro or provide the same magic variables. When **default-rule** is called, it is passed **:aacc-default-rule** as the value of **rule-key**. **default-token-rule**, similarly, provideds **:aacc-default-token-rule**. If you have a rule 'aacc-default-rule' in your grammar, for whatever reason, it will *not* override **default-rule**, instead calling the provided rule-fn.

Note that aacc either recurs from tail position or returns state, and doesn't care what the rule expressed at the root node is. That means it may be called, recursively, on any node encountered during the walk, or on any tree generated by aacc rules, or passed into state. This will *not* consume the original tree-seq or modify it in any fashion: the original tree provided to any call of aacc is frozen and will be depth-first walked exactly once before returning state. 

This allows you to do interesting things like embed a tree and rule-map from another language, call aacc using that language, then return to parsing the first langauge. State is always threaded, presuming your rules are properly written.

A useful way to do this is to define a compiler as above, and add it to state. This closes over the rule-map, making it unavailable for accidental modification until you enter the new compiler. Like so:

```clojure
(def json-compiler (partial aacc {:rule-key json-rules}))
(def meta-compiler (partial aacc {:rule-key rule-map :json-compiler json-compiler)
(def rule-map {:json json-rule})
```

Then, when you hit a **:json** tag, the **json-rule** can call **json-compiler** on the child node, which presumably contains JSON. Note that this *does* *not* consume the child node in the **meta-compiler**, which must be handled separately after **json-compiler** returns state. 

You can also pack state with a json tree and call **json-compiler** on that tree at any point. The rule is that tree-seq is immutable, you will visit every rule and token exactly once and the only way out is to add **:stop** to the state. 

This also means you can trivially send aacc into recursive descent hell by calling (aacc state :root-tree) from within a rule. Please don't do that. aacc, left to its own devices, will exit, given a string of finite length, which is currently the only input option. 

##Future

At the moment, aacc only supports the enlive output format. This is because it's easy to work with and conceivably other key-value pairs could be profitably added to the tree before aacc does its thing. aacc only uses the :tag and :content keys, because that's all instaparse outputs, but additional key-value maps in the enlive graph should not cause problems (this is worth verifying).

The hiccup output has the advantage that any vector within it is valid Clojure code. Enlive embeds literals strings in lists, which can be at the first position, causing an error. It would be good to support both formats, so that rules can be trivially called on any subsection of a tree, for diagnostic purposes. 

I would like to be able to guarantee that the same rule-map, run on the same input string and parser, will produce the same output from either enlive or hiccup format. Supporting the experimental :lisp format would also be useful, as it has the fastest seq of any of the options. It shares the enlive disadvantage that unquoted lists will in general fail if passed as naked values. 

It would be quite nice to add a magic word :pause to the state machine, that would return control to the REPL and allow re-entry at the point of departure. I'm honestly unsure where to begin with something like this, and it shouldn't be part of the basic state machine, as in many contexts it would slow things down to no purpose. 

Similarly, :stop may not be necessary if you want a crash-only compiler, and it would be nice to expose a faster, unsafe aacc that doesn't stop and check for :stop. Doing this cleanly would involve rewriting aacc as a macro, and as functionality is still being refined, this seems unwise for now. 

## License

Copyright © 2013 Sam Putman.

Distributed under the BSD 2 Clause License. 
