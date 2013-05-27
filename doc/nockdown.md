# Operator 10 Considered Harmful

## A Case for Nock 4K.

Nock is Kelvin versioned: this means changes to the specification are counted down. For anything in the superconducting range, these changes should be considered atomic, that is, only one thing should change per degree. Hoon is at 191 currently, and I believe that in such a project, which is barely chilly, number versions are more like usual versioning in that a single change reflects a series of logical commits.

Nothing much has changed between Nock 9K and Nock 5K. You can do a diff if you want: slight changes of symbol choice, an admirable increase in terseness, that's about it.

One part of Nock is utterly mathematically fixed, and that is the definition of nouns, atoms and cells. It is the simplest possible finite data structure, which I will prove by contradiction. Let's assume a simpler data structure: a single number, untroubled by any further division. This is a datum, albeit a large one, and identity being the only operation on a set of one, computation is not possible without imposing a structure. 

Let us assume a slightly more complex data structure: a list of numbers. This is arguably a structure, and by imposing an arity of two on all functions it can be made to work for computation (the proof left as an exercise for John McCarthy). 

But by imposing an arity of two on all functions we have imposed a structure, namely a singly linked list. Our ordered series of numbers could just as easily have represented key-value pairs, or a collection of independent data which can be arbitrarily sorted without loss of meaning. In other words, without arity or some other imposition, our list of numbers is data, not a data structure.

Nock imposes a better structure, a binary tree, and embeds it in the fundamental specificaiton. This is better.



