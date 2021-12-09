import scala.io.Source
import scala.collection.mutable.ArrayBuilder
import java.util.stream.Collectors
import scala.collection.mutable.Stack
import scala.collection.immutable.HashMapBuilder

class Num(val value: Int, var marked: Boolean = false) {
  def mark(number: Int): Boolean = {
    val isMatch = this.value == number
    if (isMatch) {
      this.marked = true
    }
    isMatch
  }
  override def toString = {
    val marker = if (marked) '>' else ' '
    marker + value.toString.padTo(2, ' ')
  }
}
object Num {}

class Card(val rows: Array[Array[Num]], var score: Option[Int] = None) {

  def markNumber(number: Int): Boolean = {
    if (numbers.exists(_ mark number)) {
      checkScore(number).isDefined
    } else false
  }

  private def checkScore(number: Int): Option[Int] = {
    if (
      score.isEmpty &&
      (rows.exists(_.forall(_.marked)) || cols.exists(_.forall(_.marked)))
    ) {
      score = Some(number * numbers.filter(!_.marked).map(_.value).sum)
    }
    score
  }

  private def cols: Array[Array[Num]] = {
    if (rows.isEmpty || rows(0).isEmpty) return Array.empty
    Array from Range(0, rows(0).length).map(col => rows.map(_(col)))
  }

  private def numbers: Array[Num] = rows.flatMap(row => row)

  override def toString = {
    val rowStrings = for (row <- rows) yield row.map(_.toString).mkString(" ")
    rowStrings.mkString("\n")
  }
}
object Card {}

object Main extends App {

  val RESOURCE_DIR = "src/main/resources/input"

  var numbers = loadNumbers()
  var cards = loadCards()
  val results = getResults()
  val winner = results.minBy((card, turns) => turns)
  val loser = results.maxBy((card, turns) => turns)
  println(s"Winner! Turns = ${winner._2}, Score =  ${winner._1.score.get}")
  println(winner._1)
  println()
  println(s"Loser! Turns = ${loser._2}, Score =  ${loser._1.score.get}")
  println(loser._1)

  def loadNumbers(): Array[Int] = {
    Source
      .fromFile(s"${RESOURCE_DIR}/numbers.txt")
      .getLines
      .mkString
      .split(",")
      .map(_.toInt)
  }

  def loadCards(): Array[Card] = {
    val cardLines = Source
      .fromFile(s"${RESOURCE_DIR}/cards.txt")
      .getLines

    val cardBuilder: ArrayBuilder[Card] = Array.newBuilder

    while (cardLines.hasNext) {
      val rows = Array
        .from(cardLines.take(5))
        .map(
          _.strip
            .split("\\s+")
            .filter(!_.isBlank)
            .map(value => Num(value.toInt))
        )
      cardBuilder += Card(rows)
      cardLines drop 1
    }
    cardBuilder.result
  }

  def getResults(): Map[Card, Int] = {
    val results = HashMapBuilder[Card, Int]()
    for (card <- cards) {
      val turns = numbers.indexWhere(card markNumber _)
      if (turns != -1) {
        results.addOne(card, turns + 1)
      }
    }
    return results.result
  }

}
