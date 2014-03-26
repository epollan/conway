package conway.java;

import java.util.Set;
import java.util.stream.IntStream;

public class Board {

	private final int rows;
	private final int columns;
	private final Set<Creature> creatures;
	// secondary "index" of creatures for optimized containment lookup
	private final Creature[][] grid;

	public Board(int rows, int columns, Set<Creature> creatures) {
		this.rows = rows;
		this.columns = columns;
		this.creatures = creatures;

		grid = new Creature[rows][];
		IntStream.range(0, rows).forEach(r -> grid[r] = new Creature[columns]);

		creatures.stream()
				.forEach(c -> {
					if (c.getRow() > rows - 1 || c.getColumn() > columns - 1) {
						String msg = String.format("Creature off-board: %s [board: %d x %d]", c, rows, columns);
						throw new IllegalArgumentException(msg);
					}
					grid[c.getRow()][c.getColumn()] = c;
				});
	}

	public boolean creatureAtCoordinate(Coordinate coordinate) {
		return containsCreature(coordinate.getRow(), coordinate.getColumn());
	}

	public boolean creatureRelativeToCoordinate(Coordinate coordinate, Offset offset) {
		return containsCreature(
				coordinate.getRow() + offset.getRowDelta(),
				coordinate.getColumn() + offset.getColumnDelta()
		);
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Set<Creature> getCreatures() {
		return creatures;
	}

	private boolean containsCreature(int row, int column) {
		return row >= 0 && row < rows &&
				column >= 0 && column < columns &&
				grid[row][column] != null;
	}
}
