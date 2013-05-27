# Operator 10 Considered Harmful

## A Case for Nock 4K.

Nock is Kelvin versioned: this means changes to the specification are counted down. For anything in the superconducting range, these changes should be considered atomic, that is, only one thing should change per degree. Hoon is at 191 currently, and I believe that in such a project, which is barely chilly, number versions are more like usual versioning in that a single change reflects a series of logical commits.

Nothing much has changed between Nock 9K and Nock 5K. You can do a diff if you want: slight changes of symbol choice, an admirable increase in terseness, that's about it.

One part of Nock is utterly mathematically fixed, and that is the definition of nouns, atoms and cells. It is the simplest possible finite data structure, which I will prove by contradiction. Let's assume a simpler data structure: a single number, untroubled by any further division. This is a datum, albeit a large one, and identity being the only operation on a set of one, computation is not possible without imposing a structure. 

Let us assume a slightly more complex data structure: a list of numbers. This is arguably a structure, and by imposing an arity of two on all functions it can be made to work for computation (the proof left as an exercise for John McCarthy). 

But by imposing an arity of two on all functions we have imposed a structure, namely a singly linked list. Our ordered series of numbers could just as easily have represented key-value pairs, or a collection of independent data which can be arbitrarily sorted without loss of meaning. In other words, without arity or some other imposition, our list of numbers is data, not a data structure.

Nock imposes a better structure, a binary tree, and embeds it in the fundamental specificaiton. This is better.

The rest of Nock is arbitrary in the sense that other representations for the same transformations could be chosen. That is, parens could associate to the left, turning `[a b c]` into `[[[a] b] c]`, and this would be fine. Technically, any permutation on the 11 operators provided would function in the same fashion, and it would be trivial to translate real-life codebases with a little cryptanalysis, even if the permutation wasn't known. 

Putting the macros before the fundamental operators would be perverse, of course, and I have no quarrel with @cgyarvin's ordering. I'm a Hermetic fussbudget and the urge is there, but I'm ignoring it. 

What I can't ignore is my urge to shave an operator off, because 11 is one too many. It means that in ASCII representations of Nock code, indeed in Arabic numeral representations of Nock code, there is a fundamental operator that is two characters wide. That is just gratingly wrong in a way that almost makes my teeth hurt: the missed opportunities for code analysis are legion, and don't tell me this doesn't matter in practice because binary doesn't care about 9 vs 10. If that's how you feel what's wrong with targeting x86_64? It's plenty frozen and emulation will continue to work for the forseeable future. 

I grant immediately: this is a weak functional argument, if a strong aesthetic one. If I were merely nitpicking aesthetics, this would be annoying, jealous behavior, and I should be ignored or loudly booed. Nock should have 17 operators if that's the correct number to make it work, because any adoption of any Nock at all will be a qualitative leap in our power and ability as programmers. Objecting on the basis of Kabbalistic mumbo-jumbo, or even ASCII code-width, is cranky, in both senses of the word.




