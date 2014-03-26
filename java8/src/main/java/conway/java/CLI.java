package conway.java;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class CLI {

	// Defaults
	private static Printer printer = Printers.console(false);
	private static int turns = 250;
	private static long sleep = 20;
	private static int rows = 30;
	private static int columns = 40;
	private static Set<Creature> creatures = ImmutableSet.of(
			new Creature(0, 9),
			new Creature(0, 10),
			new Creature(0, 11),
			new Creature(1, 11),
			new Creature(1, 12),
			new Creature(5, 6),
			new Creature(2, 6),
			new Creature(2, 5),
			new Creature(1, 6),
			new Creature(3, 6)
	);

	public static void main(String... args) throws Exception {
		parseArgs(args);

		Board board = new Board(rows, columns, creatures);
		Game game = new Game(rows, columns);

		long nanos = 0;

		for (int turn = 1; turn <= turns; turn++) {
			printer.print(board, turn);

			long start = System.nanoTime();
			board = game.turn(board);
			nanos += (System.nanoTime() - start);

			Thread.sleep(sleep);
		}

		System.out.format("Performance: %.3f ms/turn%n", nanos * 0.000001 / turns);
	}

	private static String shift(AtomicInteger arg, String[] args) {
		return args[arg.incrementAndGet()];
	}

	private static void usage() {
		System.out.format("Usage: game [options]%n");
		System.out.format("    where options are:%n");
		System.out.format(" --turns/-t      N   the number of turns, defaults to %d%n", turns);
		System.out.format(" --sleep/-s      MS  MS to sleep between turns, defaults to %d%n", sleep);
		System.out.format(" --creatures         literal comma-delimited, s-exp style creatures, or an '@'-prefixed file containing same%n");
		System.out.format(" --rows/-r       N   the number of rows in the game board, defaults to %d%n", rows);
		System.out.format(" --columns/-c    N   the number of columns in the game board, defaults to %d%n", columns);
		System.out.format(" --boundaries/-b     flag indicating that game cell boundaries should be printed%n");
		System.out.format(" --devnull           flag indicating that turn-by-turn state should not be printed%n");
	}

	private static void parseArgs(String[] args) throws IOException {
		AtomicInteger arg = new AtomicInteger(0);
		while (arg.get() < args.length) {
			switch (args[arg.get()]) {
				case "--turns":
				case "-t":
					turns = Integer.parseInt(shift(arg, args));
					break;
				case "--sleep":
				case "-s":
					sleep = Long.parseLong(shift(arg, args));
					break;
				case "--creatures":
					String creaturesArg = shift(arg, args);
					if (creaturesArg.startsWith("@")) {
						creatures = Creature.parse(Files.toString(new File(creaturesArg.substring(1)), Charsets.UTF_8));
					} else {
						creatures = Creature.parse(creaturesArg);
					}
					break;
				case "--rows":
				case "-r":
					rows = Integer.parseInt(shift(arg, args));
					break;
				case "--columns":
				case "-c":
					columns = Integer.parseInt(shift(arg, args));
					break;
				case "--boundaries":
				case "-b":
					printer = Printers.console(true);
					break;
				case "--devnull":
					printer = Printers.devNull();
					break;
				case "--help":
				case "-h":
					usage();
					System.exit(0);
					break;
				default:
					System.out.format("Unexpected option: %s%n", args[arg.get()]);
					usage();
					System.exit(1);
			}
			arg.incrementAndGet();
		}
	}
}
