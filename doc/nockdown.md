# Operator 10 Considered Harmful

## A Case for Nock 4K.

Nock is Kelvin versioned: this means changes to the specification are counted down. For anything in the superconducting range, these changes should be considered atomic, that is, only one thing should change per degree. Hoon is at 191 currently, and I believe that in such a project, which is barely chilly, number versions are more like usual versioning in that a single change reflects a series of logical commits.

Nothing much has changed between Nock 9K and Nock 5K. You can do a diff if you want: slight changes of symbol choice, an admirable increase in terseness, that's about it.

One part of Nock is utterly mathematically fixed, and that is the definition of nouns, atoms and cells. It is among the simplest possible finite data structures, that is, a data structure may be as simple but not simpler. This I will prove by contradiction. 

Let's assume a simpler data structure: a single number, untroubled by any further division. This is a datum, albeit a large one, and identity being the only operation on a set of one, computation is not possible without imposing a structure. 

Let us assume a slightly more complex data structure: a list of numbers. This is arguably a structure, and by imposing an arity of two on all functions it can be made to work for computation (the proof left as an exercise for John McCarthy). 

But by imposing an arity of two on all functions we have imposed further structure, namely a singly linked list. Our ordered series of numbers could just as easily have represented key-value pairs, or a collection of independent data which can be arbitrarily sorted without loss of meaning. In other words, without arity or some other imposition, our list of numbers is data, not a data structure.

Nock imposes a better structure, a binary tree, and embeds it in the fundamental specificaiton. This is better.

The rest of Nock is arbitrary in the sense that other representations for the same transformations could be chosen. That is, parens could associate to the left, turning `[a b c]` into `[[[a] b] c]`, and this would be fine. Technically, any permutation on the 11 operators provided would function in the same fashion, and it would be trivial to translate real-life codebases with a little cryptanalysis, even if the permutation wasn't known. 

Putting the macros before the fundamental operators would be perverse, of course, and I have no quarrel with @cgyarvin's ordering. I'm a Hermetic fussbudget and the urge is there, but I'm ignoring it. 

What I can't ignore is my urge to shave an operator off, because 11 is one too many. It means that in ASCII representations of Nock code, indeed in Arabic numeral representations of Nock code, there is a fundamental operator that is two characters wide. That is just gratingly wrong in a way that almost makes my teeth hurt: the missed opportunities for code analysis are legion, and don't tell me this doesn't matter in practice because binary doesn't care about 9 vs 10. If that's how you feel, what's wrong with targeting x86_64? It's plenty frozen and emulation will continue to work for the forseeable future. 

I grant immediately: this is a weak functional argument, if a strong aesthetic one. If I were merely nitpicking aesthetics, this would be annoying, jealous behavior, and I should be ignored or loudly booed. Nock should have 17 operators if that's the correct number to make it work, because any adoption of any Nock at all will be a qualitative leap in our power and ability as programmers. Objecting on the basis of Kabbalistic mumbo-jumbo, or even ASCII code-width, is cranky, in both senses of the word.

My objection to Operator 10 is more fundamental, and ultimately functional. I can make a strong case that hinting, the purpose of Operator 10, should not be necessary for jet-assisted Nock interpretation, and furthermore, that any use of Operator 10 for this purpose cannot simultaneously be more efficient than not using hinting and be honest in the sense that it at least checks your Nock code's structure before doing something nominally equivalent.

I'm going to attempt a cogent functional case against Operator 10, on two bases: one, demonstrating that no such operator is needed for jet-propulsion of Nock code, and two, showing that the compression-oriented approach can only be faster than hinting if the Nock interpreter is to be honest. The third, weakest case is simply that 10 is a macro and can be completely discarded in favor of convention if one really decided one needed it.

Caveats: My understanding of Nock is by no means total. I have written a parser, but not yet an interpreter, because I am working on an industrial scale target acquisition and deployment system for the swatting of all flying creatures. I do not yet understand how Hoon works, but the scheme I'm proposing is so Martian that it seems possible that Hoon is doing what I propose already. I have been unable to find references, or pieces of the Martian code that would confirm or deny this. 

First, let's look at Operator 10. Again: My understanding of Nock is by no means total, I have a working parser but no interpreter yet. Furthermore, I grok Hoon only poorly. I cannot tell if my proposal would break everything, but I hope at least that if Hoon is well designed (and I trust this), the breakage would be limited to the Nock/Jet region and would not extend to the syntax of Hoon code. 

## What is this Operator 10? 

The following is taken directly from the Crash Course:

***

    29 ::    *[a 10 b c]       *[a c]

If `x` is an atom and `y` is a formula, the formula `[10 x y]` 
appears to be equivalent to... `y`.  For instance:

	~tasfyn-partyv> .*([132 19] [10 37 [4 0 3]])
	20

Why would we want to do this?  `10` is actually a hint operator.
The `37` in this example is discarded information - it is not
used, formally, in the computation.  It may help the interpreter
compute the expression more efficiently, however.

Every Nock computes the same result - but not all at the same
speed.  What hints are supported?  What do they do?  Hints are a
higher-level convention which do not, and should not, appear in
the Nock spec.  Some are defined in Hoon.  Indeed, a naive Nock
interpreter not optimized for Hoon will run Hoon quite poorly.
When it gets the product, however, the product will be right.

There is another reduction for hints - line 30:

	30 ::    *[a 10 [b c] d]   *[a 8 c 7 [0 2] d]

Once we see what `7` and `8` do, we'll see that this complex hint
throws away an arbitrary `b`, but computes the formula `c`
against the subject and... throws away the product.  This formula
is simply equivalent to `d`.  Of course, in practice the product
of `c` will be put to some sordid and useful use.  It could even
wind up as a side effect, though we try not to get _that_ sordid.

(Why do we even care that `c` is computed?  Because `c` could
crash.  A correct Nock cannot simply ignore it, and treat both
variants of `10` as equivalent.)

***

So that's the what and why of Operator 10. For the simplest 10, one provides a Nock formula and an extra number. The Nock interpreter can use one or both of those things to return a value, as long as the value is the same as the value that the Nock formula would return if one actually executed it.

Logically, a Nock interpreter can do two things with this hint: simply use it to produce the value, or use it to produce the value after first looking at the formula and confirming that yes, that formula is expected. Consider: if 42 is used as a subtraction function hint, and 56 as a multpily function hint. The Nock interpreter gets a 10 where b is 42, c is the decrement formula, and a are the numbers to be subtracted. 

Whether or not the Nock interpreter looks at c, the result will be correct, so let's assume it doesn't. What happens if we pass it a 10 where b is 56, c is the decrement function, and a are the numbers to be...what exactly? subtracted? multiplied? A jetted Nock will produce the product and a non-jetted Nock will produce the difference. 

That's not really acceptable; it's a Faustian pact that will hold so long as Faust is the only one who reads and writes the runes. Forget nasty code injection for a second: if that's how we want Nock to operate, why the formality of keeping the Nock algorithm around? It seems like a lot of padding, just to be able to hand-check erroneous output. 

For Nock to be honest, it has to at least *glance* at the formula. A sidelong look should suffice. Execution is exactly what we're trying to avoid, but Nock is compiler generated, as @cgyarvin points out, and we need only make sure that the algorithm is in fact that generated by the compiler. We may then jet it with a comfortable degree of safety. 

10 introduces hints which, to quote, "do not, and should not, appear in the Nock spec". In practice, without formula checking, this can produce undefined behavior by changing parts of a Nock noun which are formally uncomputed. We now have a theoretically undecidable state machine and there's no getting around this: I see no way that Nock can guarantee, as it should, that no modification of b in a 10 formula will produce a different result

None except for formula verification, which sucks on the surface, because these formulas can get big and looking at them might take quite a bit longer than launching the jet. There's a way around this: fundamentally, this way is compression. 

## Life without Operator 10

I believe I have made the case that Operator 10 either produces a non-deterministic state machine in practice or requires verification of each and every formula that is hinted, before execution. There are ways to keep this from becoming a problem, particularly if we assume good-faith actors, which is comical; but this is math, so anything goes. 

Can we make a fast Nock interpreter that uses no hinting at all? It happens that we can, and in the process, we stand to gain considerably. Here's an [interesting paper](http://arxiv.org/pdf/1304.7392v1.pdf). It's a good paper, because the abstract tells you everything you need to know, and the contents do not obfuscate, exceed, or contradict the abstract. 

If you understand context-free grammars, what we're doing here is bloody simple. We're transforming a tree, which presumably contains many repeated subgraphs, into a context free grammar that will produce the tree given a simple binary left-right input. Since all rules terminate, no further structure is necessary: every atom is represented exactly once, and every repeated subgraph is represented as a path to those atoms.  

That's hella tight bro! It's stupid simple and contains no gotchas at all for Nock's similarly moronic data structure. Any grammar rule with one resolution is structure, any with two is a variable. Hinting is actively harmful if this is how you've represented your code, because it adds entropic difference to your function call that you do not end up using. 

Note that because this is a context free grammar, you can pre-load it with rules for matching trees. The practical application is that all jet-assisted formulas are pre-loaded at the top of the grammar, followed by all formulas generated by the Hoon kernel: This would make the byte-stream itself compatible with Hoon output, given front-loading with the additional rules needed. 

Again, I have no idea if Hoon does this. But if it does, what's with the hinting? How could that be helpful if you can read a bytestream and know for sure what you're supposed to do with it? How could adding a variable, or even another constant, to that bytestream, assist in any fashion? 

##Advantages

I have perhaps overstated the case against Operator 10. It's true we must verify the code, but we only have to do it once. We can then hash it and store the signature, and check real quick that nothing has messed with our magic hints. 

That requires us to look at the entire codebase once, every time we load it, before we launch it. That could conceivably be costly. The code analysis step is longer, but it's part of compilation, so no big deal. 

We would have to do this anyway, in any case where preventing malicious code was important. If we have other securities in that regard, we can skip this step. 

Also, these Nock files are going to be pretty big, and filled with repeated subgraphs that are no longer being looked at. My trigger finger is itching to compress anyway, so what do we get out of it? 

Well, to verify all the jet-assisted code, we look at the front of the file and verify it against the checksum. To verify all of the Hoon kernel code, we look at more of the front of the file. Verification of the application-specific code is impossible without execution, but we can continue on to hash the entire file if we merely want surety that it's the same file we looked at last time (or at minimum that our attacker changed the file, hashed it, and put the hash where we'd expect to find it). 

What we'd actually execute is not Nock, but Nockdown: partially compressed Nock, where the parts that stay compressed are jet-assisted and the parts that are unrolled are not. We transfer nck, which is fully compressed Nock, but generally keep applications around as Nockdown, so we can directly load into memory instead of hand-unrolling on the fly.  Unrolling into pure Nock is necessary only when slowing jets for systems development purposes. 

A Hoon specific Nock interpreter will use an even more optimized Nockdown, trading off compression against execution speed to tune performance. Since any Nockdown can be fully unrolled, this should provide the compatibility we're looking for, and look ma! There are no hints, and hence, no magic, only two invisible lines in the grammar rules advising boundaries for soft and hard jetting.  


