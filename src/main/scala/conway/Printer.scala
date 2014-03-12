package conway

trait Printer {
	def print(board: Board, turn: Int)
}

class DevNullPrinter() extends Printer {
	def print(board: Board, turn: Int) = {}
}

class ConsolePrinter(boundaries: Boolean = false) extends Printer {

	private val builder = new StringBuilder()

	def print(board: Board, turn: Int) = {
		builder ++= "Turn #" ++= String.valueOf(turn) += '\n'
		for (rowNumber <- 0 until board.rows) {
			appendRowSeparator(board.columns)
			appendRow(board, rowNumber)
		}
		appendRowSeparator(board.columns)
		println(builder.toString())
		builder.setLength(0)
	}

	private def appendRow(board: Board, rowNumber: Int) = {
		for (columnNumber <- 0 until board.columns) {
			if (boundaries) {
				builder += '|'
			}
			if (board.containsCreature(Point(rowNumber, columnNumber))) {
				builder += 'o'
			} else {
				builder += ' '
			}
		}
		if (boundaries) {
			builder += '|'
		}
		builder += '\n'
	}

	private def appendRowSeparator(columns: Int) = {
		if (boundaries) {
			for (columnNumber <- 0 until columns) {
				builder ++= "+-"
			}
			builder ++= "+\n"
		}
	}
}


