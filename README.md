# Motivation

1. Learning Scala ;)
2. How I can build a simple and robust parser.
3. Parsing the output from Switches, Storages,... commandline commands

# Example

A simple output:

```
first: test1
second: test 2
third: test 3 
  with this content
  block
```

# Current project state

* This is the first working version
* I can define State and StateTransitions
* The parser returns an error or a result

What can be done to make this programm better:

* Could it be possible to declare all the stuff outside the class?

# Building

```sbt compile```

# Working with Eclipse

* ```sbt eclipse```
* Eclipse: Import -> Existing Project -> ...
