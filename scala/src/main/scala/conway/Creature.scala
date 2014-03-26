package conway

import _root_.java.util.regex.Pattern
import scala.collection.immutable
import scala.collection.mutable

case class Creature(row: Int, column: Int) extends Coordinate

object Creature {

	private val PATTERN = Pattern.compile("(\\((\\d+), ?(\\d+)\\))[, ]*")

	/**
	 * Parse comma-delimited, s-exp style row/column tuples into a set of creatures.
	 * @param s string serial form
	 * @return an immutable, possibly empty set of creatures
	 */
	def parse(s: String): immutable.Set[Creature] = {
		val matcher = PATTERN.matcher(s)
		val creatures = mutable.Set.empty[Creature]
		while (matcher.find()) {
			val row = Integer.parseInt(matcher.group(2))
			val column = Integer.parseInt(matcher.group(3))
			if (row < 0 || column < 0) {
				throw new IllegalArgumentException("Need non-negative row/column")
			}
			creatures += Creature(row, column)
		}
		creatures.toSet
	}

	def at(coordinate: Coordinate): Creature = Creature(coordinate.row, coordinate.column)
}
