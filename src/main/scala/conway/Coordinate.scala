package conway

trait Coordinate {
	def row: Int
	def column: Int
}

case class Point(row: Int, column: Int) extends Coordinate
