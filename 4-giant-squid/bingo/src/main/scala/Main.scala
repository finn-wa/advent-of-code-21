import scala.io.Source

val RESOURCE_DIR = "src/main/resources/sample"

object Main extends App {

  val numbers = Source
    .fromFile(s"${RESOURCE_DIR}/numbers.txt")
    .getLines
    .mkString
    .split(",")
    .map(_.toInt)

  println(numbers.mkString("\n"))
}
