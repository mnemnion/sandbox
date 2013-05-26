## Hoon : Doing Things With Nock

So there's Nock. @cgyarvin describes it quite neatly. Most of my urge to re-describe it has dissipated upon repeated readings of the Crash Course. 

The idea behind Nock is that there are two uses for Nock: naive execution, and code substitution. Naive execution is for verification, because it's ungodly slow and won't terminate in a useful universe for many things we can easily doi n practice. Real execution is jet propelled.

There appear to be two mechanisms for this: hinting, and direct recognition of a noun with jet propulsion. Rule 10 of Nock allows you to supply an atom or formula which is nominally discarded; this can be used, for example, to substitute the canonical O(n) decrement function with `i--`. In principle, one could directly observe the value of some noun and substitute a jet, which could combine quite neatly with existing compression techniques; I can't tell if this technique is used in the Martian Stack. 

Either way, one must make a subset of Nock fast, or Nock is too slow to be useful.

This is where Hoon comes in. Go ye and read some. Looks like APL in ASCII, huh? 

It's actually astonishingly readable for pure numbers. Hoon is simply some magic macro assembler for Nock, and it uses the ASCII values of every token except whitespace as numbers on the resulting Nock. There's a reason everything is as short as practical: large atoms are costly, especially as formulas.

The APL appearance is deceptive; Hoon is terse because it's not really language as we know it. Note that this hack (and it is a hack, the best hack in the stack) allows alphanumeric computation without introducing additional dependencies into Nock. ASCII is more frozen than Nock, it will survive the heat death of the universe in simulation, and it's just integers. Any Hoon program could be rewritten as these integers without loss of meaning from a computer's perspective. 

I accept, from reading the Hoon documentation (which is for Watt, which Hoon was once), that Hoon may be pleasurable to program in. I'm also fairly sure that I'll need an Encyclopedia of Hoon to really get the hang of it. The Hoon kernel is a pretty good Dictionary of Hoon, but it's in Hoon; anyone who has tried a real foreign language this way knows a Hoon-English dictionary is better to start out with. 

The Martian Stack divides tasks differently from Earth technology. Hoon without jets is as useless as any other Nock; Hoon defines the jets needed to make Hoon fast, and those jets comprise the actual virtual machine upon which Hoon code is executed.

The jets are modular and may do whatever they wish as long as they return the same result as the representative Nock would. To run Hoon, you must have Hoon, Nock, and a Nock interpreter that jet-propels the specified Nock output of Hoon. Nock is near frozen and Hoon will get there, eventually, at which point an Encyclopedia will be feasible and we can all get building.
