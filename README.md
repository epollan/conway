Overview
======

Naive Scala and Java 8 implementations of Conway's Game of Life.   After stumbling across
http://www.youtube.com/watch?v=E8kUJL04ELA by way of http://www.reddit.com/r/programming,
I decided to use it to try to pair program a bit with my daughters.  They got bored, but
I resolved to use it as a Scala exercise, anyhow.  Warning:  the arg parsing is brute force --
I'm sure there's 8 or 10 DSLs to do the same thing.  Java 8 port was primarily to peruse the 
new streams API.  Nice stuff!

Prereq's
======

Install the Scala Build Tool.  On OSX, simply `brew install sbt`.  For the Java 8 version, 
you'll just need Oracle's Java 8 JDK.  

Running
======

There's a bash wrapper script that will build and run the game simulator in each
language-specific subdirectory:

    > game

Help/usage is available via the standard ```-h/--help``` option:

    > game
    [info] Loading project definition from /Users/epollan/src/conway/project
    [info] Set current project to conway (in build file:/Users/epollan/src/conway/)
    [success] Total time: 0 s, completed Mar 12, 2014 12:20:27 AM
    Usage: game [options]
        where options are:
     --turns/-t      N   the number of turns, defaults to 250
     --sleep/-s      MS  MS to sleep between turns, defaults to 20
     --creatures         literal comma-delimited, s-exp style creatures, or an '@'-prefixed file containing same
     --rows/-r       N   the number of rows in the game board, defaults to 30
     --columns/-c    N   the number of columsn in the game board, defaults to 40
     --boundaries/-b     flag indicating that game cell boundaries should be printed
     --devnull           flag indicating that turn-by-turn state should not be printed

Initial game state can be loaded from a file.  Try the "glider" initial game state:

    > (cd scala && game --creatures @../glider.tuples)
