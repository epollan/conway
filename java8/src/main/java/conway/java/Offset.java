package conway.java;

public class Offset {

	private final int rowDelta;
	private final int columnDelta;

	public Offset(int rowDelta, int columnDelta) {
		this.rowDelta = rowDelta;
		this.columnDelta = columnDelta;
	}

	public int getRowDelta() {
		return rowDelta;
	}

	public int getColumnDelta() {
		return columnDelta;
	}
}
