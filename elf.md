#The EMACS Liberation File

Here I find myself, editing code in Emacs. I suspected this day would arrive. I have long resisted, thanks to the residual taste of many bad experiences. Finally tackling it became inevitable the day I fell for Lisp. The way things stand, Emacs is a corrolary of Lisp, an implication of it. 

Emacs is not an editor. Vim is an editor, as is Sublime Text, which I use when (mere) editing is the task at hand. Had I a taste for Vim, things might be different. But I do not. Admiration yes; taste, :q. 

Emacs is an magic spell written by an old and somewhat grumpy wizard. We owe this wizard a lot. Fact is, though, if it weren't for Emacs Live I think I'd still be choking on frustration and rage. 

Emacs Live got me through the seeing red phase. The next task is to liberate this beast for my own purposes. Emacs is a living, breathing creature, a familiar of sorts. The Emacs you get out of the box, that's Stallman's Emacs. The Emacs you get with Emacs Live is Sam Aaron's Emacs. 

I gather the way it works is, you wear it in and then you wear it proudly. If I'm going Emacs, I'm going all the way. Let's start with navigation, my major source of pain.

##Arrow Keys Are Awesome

So check it. I'm Emacsin from a basically Mac environment and don't see that changing. So I have four gizmo keys on the left and two on the right. When I navigate, I'm on the arrow keys. That's just what's up, and I will not fuck around on this nonsense. 

Part of why I've avoided the cult of Emacs is that using the arrow keys, if the tutorials are to be believed, is some kind of sin against the Holy Spirit. It makes the fingers leave the home row. Oh snap! 

I not only do not care, I am actively hostile to this point of view. Arrows are on my keyboard so I can move around; letters are for things that might be letter like in some way, like "S"aving a file or whatnot. I even use the trackpad, which is admittedly comical in Emacs and not a habit I intend to get into.

So I've got hyper (aka "fn"), control, meta (alt/option/names-all-day), and command aka super. My general principle, as I make sense out of this mess, is that hyper is for me, command is for the Mac, and control and meta belong to the larger Emacs community. I'll fiddle with them, in particular, I do not need any of the letter currently given over to navigation, at least the ones that overload the arrow keys.

meta-etc moves by word as installed, which is great. command-etc need to be beginning-of-line, end-of-line, up-page, and down-page respectively. Control right and left should cycle focus through the visible buffers. Control up and down, top and bottom of buffer. Hyper-etc will be paredit, slurp and barf on x or y. Still figuring that business out. 

##Tidying Up

Emacs Live as configured preserves an alarming property of emacsen generally: namely, if you press control or meta anything, at all, it will probably do something, and God help you. The reason our quest will one day lead beyond emacs is simple: there is no "oops" command, which does its level best to undo whatever command you just entered, and there never will be.

Emacs is fully loaded and pointed in every possible direction. Emacs Live only adds to this happy chaos. At some point I have to actually field strip this puppy. 

I keep forgetting. This is supposed to be source code as well as rant. So here's the wee kernel of the ELF:

```lisp
;;@man Hyper Button
(setq ns-function-modifier 'hyper)

;;@man Key Bindings
(global-set-key (kbd "H-x") 'slime-restart-inferior-lisp)
```

That first one, that's a wizard achievement unlocked thanks to [Xah Lee](http://addme.com). `hyper` is not widely used in Emacs land, I gather it's kept on the shelf as a user namespace. Good. If I find things in it, I'll put them somewhere else. 

The restart key is for ECL. I should make it modal at some point.
