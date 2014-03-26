package conway.java;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

public class Creature implements Coordinate {

	private static final Pattern PATTERN = Pattern.compile("(\\((\\d+), ?(\\d+)\\))[, ]*");

	/**
	 * Parse comma-delimited, s-exp style row/column tuples into a set of creatures.
	 * @param s string serial form
	 * @return an immutable, possibly empty set of creatures
	 */
	public static Set<Creature> parse(String s) {
		Matcher matcher = PATTERN.matcher(s);
		ImmutableSet.Builder<Creature> creatures = ImmutableSet.builder();
		while (matcher.find()) {
			int row = Integer.parseInt(matcher.group(2));
			int column = Integer.parseInt(matcher.group(3));
			if (row < 0 || column < 0) {
				throw new IllegalArgumentException("Need non-negative row/column");
			}
			creatures.add(new Creature(row, column));
		}
		return creatures.build();
	}

	public static Creature at(Coordinate coordinate) {
		return new Creature(coordinate.getRow(), coordinate.getColumn());
	}

	private final int row;
	private final int column;

	public Creature(int row, int column) {
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

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("row", row)
				.add("column", column)
				.toString();
	}
}
