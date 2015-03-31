package at.linuxhacker.stateTransition.test

import at.linuxhacker.stateTransition.lib.Parser

object Test {

  def main( argv: Array[String] ):Unit = {
    println( new Parser( ).parse( List( "first: test1", "second: test2", "\ttest2 4", "\ttest2 5" ) ) )
    println( new Parser( ).parse( List( "first: test1", "second: test2", "\ttest2 4", "\ttest2 5", "third: error" )) )
    println( new Parser( ).parse( List( "first: test1", "second: test2", "\ttest2 4", "\ttest2 5", "" )) )
    println( new Parser( ).parse( List( "first: test1", "second: test2", "\ttest2 4", "\ttest2 5", "", "first: test" )) )
  } 
}