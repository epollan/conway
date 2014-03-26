package conway.java;

public interface Coordinate {

	int getRow();
	int getColumn();

	default boolean isEquals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !getClass().equals(o.getClass())) {
			return false;
		}

		Coordinate other = (Coordinate)o;

		return getColumn() == other.getColumn() && getRow() == other.getRow();
	}

	default int computeHashCode() {
		return 31 * getRow() + getColumn();
	}
}
