package conway.java;

public abstract class Printers {

	public static Printer devNull() {
		return (board, turn) -> {};
	}

	public static Printer console(final boolean boundaries) {

		return new Printer() {

			private final StringBuilder builder = new StringBuilder();

			@Override
			public void print(Board board, int turn) {
				builder.append("Turn #").append(String.valueOf(turn)).append('\n');
				for (int rowNumber = 0; rowNumber < board.getRows(); rowNumber++) {
					appendRowSeparator(board.getColumns());
					appendRow(board, rowNumber);
				}
				appendRowSeparator(board.getColumns());
				System.out.println(builder.toString());
				builder.setLength(0);
			}

			private void appendRow(Board board, int rowNumber) {
				for (int columnNumber = 0; columnNumber < board.getColumns(); columnNumber++) {
					if (boundaries) {
						builder.append('|');
					}
					if (board.creatureAtCoordinate(new Point(rowNumber, columnNumber))) {
						builder.append('o');
					} else {
						builder.append(' ');
					}
				}
				if (boundaries) {
					builder.append('|');
				}
				builder.append('\n');
			}

			private void appendRowSeparator(int columns) {
				if (boundaries) {
					for (int columnNumber = 0; columnNumber < columns; columnNumber++) {
						builder.append("+-");
					}
					builder.append("+\n");
				}
			}
		};
	}
}
