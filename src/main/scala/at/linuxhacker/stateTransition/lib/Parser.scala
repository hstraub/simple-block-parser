package at.linuxhacker.stateTransition.lib

import scala.util.matching.Regex

case class Result( first: String, second: String, content: List[String] )

class Parser {

  private trait State {
    def regex: Regex
  }
  private object Unknown extends State { def regex = """()""".r }
  private object First extends State { def regex = "^first: (.*)".r }
  private object Second extends State { def regex = "^second: (.*)".r }
  private object Content extends State { def regex = "^\t(.*)".r }
  private object End extends State { def regex = "^()$".r }

  private case class StateTransition( oldState: State, currentState: State, f: () => Unit )

  private var first: String = null
  private var second: String = null
  private var content: scala.collection.mutable.ListBuffer[String] = 
      scala.collection.mutable.ListBuffer( )
  private var record: String = null
  private var result: Result = null
  
  private val stateTransitions = List(
      StateTransition( Unknown, First, () => { first = getValue( record ) } ),
      StateTransition( First, Second, () => { second = getValue( record ) } ),
      StateTransition( Second, Content, () => { content += getValue( record ) } ),
      StateTransition( Content, Content, () => { content +=  getValue( record ) } ),
      StateTransition( Content, End, () => { result = Result( first, second, content.toList ) } )
  )
      
  private var isFresh = true;
  private var oldState: State = Unknown
  private var currentState: State = Unknown
  private val parserErrors: scala.collection.mutable.ListBuffer[String] = 
      scala.collection.mutable.ListBuffer( )
  
  def parse( buffer: List[String] ): Either[List[String],Result] = {
    if ( isFresh ) {
      isFresh = false
      doParse( buffer )
    } else Left( List( "This is only a oneshoot Object!" ) )
  }
      
  private def doParse( buffer: List[String] ): Either[List[String],Result] = {
    buffer.foreach( x => {
      record = x 
      val candidates = stateTransitions
          .filter( x => x.oldState == currentState )
          .filter( x => x.currentState.regex.pattern.matcher( record ).matches )
      if ( candidates.length == 1 ) {
        setNewState( candidates(0).currentState )
        candidates(0).f( )
      } else parserErrors += "No match for: " + record + " in state: " + oldState + " -> " + currentState
    } )
    setNewState( End )
    val last = stateTransitions.filter( x => x.oldState == oldState && x.currentState == currentState )
    if ( last.length != 1 )
      parserErrors += "Cannot match the End State, we have: " + oldState + " -> " + currentState
    else
      last(0).f()
    if ( parserErrors.length == 0 )
      Right( result )
    else
      Left( parserErrors.toList )
  }
  
  private def setNewState( newState: State ): Unit = {
    oldState = currentState
    currentState = newState
  }
  
  private def getValue( record: String ): String = {
        val regex = currentState.regex
        record match  {
          case regex( x ) => x
          case _ => ""
        }
  }

}