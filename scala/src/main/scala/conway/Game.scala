package conway

import scala.collection.immutable
import scala.collection.mutable

/**
 * A Conway's Game of Life coordinator, capable of dealing with boards of size rows x columns.
 */
class Game(rows: Int, columns: Int) {

	private val neighborOffsets: Set[(Int, Int)] = immutable.Set(
		(-1, -1), (-1, 0), (-1, 1),
		(0, -1),           (0, 1),
		(1, -1),  (1, 0),  (1, 1)   
	)

	private val cells: mutable.Set[Point] = mutable.Set.empty

	for (row <- 0 until rows) {
		for (column <- 0 until columns) {
			cells += Point(row, column)
		}
	}

	def turn(board: Board): Board = {
		// Select creatures that should die
		val deaths = board.creatures.filter(c => shouldDie(neighborCount(board, c)))

		// Create newly born creatures where an empty cell has exactly 3 neighbors
		val births = cells
			.filter(cell => !board.containsCreature(cell) && shouldSpawn(neighborCount(board, cell)))
			.foldLeft(mutable.Set.empty[Creature])(
				(creatures, cell) => creatures += Creature.at(cell)
			)

		// Create a new board with same dimensions, minus deaths, plus births
		Board(board.rows, board.columns, board.creatures.++(births).--(deaths))
	}

	private def neighborCount(board: Board, c: Coordinate): Int = {
		neighborOffsets.count(offset => board.containsCreature(Point(c.row + offset._1, c.column + offset._2)))
	}

	private def shouldDie(neighbors: Int): Boolean = neighbors <= 1 || neighbors >= 4

	private def shouldSpawn(neighbors: Int): Boolean = neighbors == 3
}
