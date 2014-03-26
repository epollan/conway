package conway.java;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {

	private static final List<Offset> NEIGHBOR_OFFSETS = ImmutableList.of(
			new Offset(-1, -1), new Offset(-1, 0), new Offset(-1, 1),
			new Offset(0, -1),                     new Offset(0, 1),
			new Offset(1, -1),  new Offset(1, 0),  new Offset(1, 1)
	);

	private final Set<Point> cells = new HashSet<>();

	public Game(int rows, int columns) {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				cells.add(new Point(row, column));
			}
		}
	}

	public Board turn(Board board) {
		// Select creatures that should die
		Set<Creature> deaths = board.getCreatures().stream()
				.filter(c -> shouldDie(neighborCount(board, c)))
				.collect(Collectors.toSet());

		// Create newly born creatures where an empty cell has exactly 3 neighbors
		Set<Creature> births = cells.parallelStream()
				.filter(cell -> !board.creatureAtCoordinate(cell) && shouldSpawn(neighborCount(board, cell)))
				.map(Creature::at)
				.collect(Collectors.toSet());

		// Create a new board with same dimensions, minus deaths, plus births
		Set<Creature> newCreatures = Sets.union(board.getCreatures(), births).stream()
				.filter(c -> !deaths.contains(c))
				.collect(Collectors.toSet());
		return new Board(board.getRows(), board.getColumns(), newCreatures);
	}

	private long neighborCount(Board board, Coordinate c) {
		return NEIGHBOR_OFFSETS.stream()
				.filter(o -> board.creatureRelativeToCoordinate(c, o))
				.count();
	}

	private boolean shouldDie(long neighbors) {
		return neighbors <= 1 || neighbors >= 4;
	}

	private boolean shouldSpawn(long neighbors) {
		return neighbors == 3;
	}
}
