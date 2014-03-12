package conway


case class Board(rows: Int, columns: Int, creatures: Set[Creature]) {

	// secondary "index" of creatures for optimized containment lookup
	private val grid: Array[Array[Creature]] = new Array(rows)

	// Instance initialization
	creatures.find(c => c.row > rows - 1 || c.column > columns - 1) match {
		case Some(c) =>
			throw new IllegalArgumentException(s"Creature off-board: $c [board: ${this.rows} x ${this.columns}]")
		case _ =>
	}

	// Build the grid of cells, and populate with creatures
	for (rowNumber <- 0 until rows) {
		grid(rowNumber) = new Array[Creature](columns)
	}
	creatures.foreach(creature => grid(creature.row)(creature.column) = creature)


	def containsCreature(coordinate: Coordinate): Boolean = {
		// Early out if the query is off-board
		if (coordinate.row < 0 || coordinate.row >= rows) {
			return false
		}
		if (coordinate.column < 0 || coordinate.column >= columns) {
			return false
		}

		grid(coordinate.row)(coordinate.column) != null
	}
}
