package conway.java;

public class Point implements Coordinate {

	private final int row;
	private final int column;

	public Point(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public boolean equals(Object o) {
		return isEquals(o);
	}

	@Override
	public int hashCode() {
		return computeHashCode();
	}
}
