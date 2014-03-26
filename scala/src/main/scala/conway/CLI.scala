package conway

import java.util.concurrent.atomic.AtomicInteger
import java.io.File
import com.google.common.io.Files
import com.google.common.base.Charsets

object CLI extends App {

	// Defaults
	var printer: Printer = new ConsolePrinter()
	var turns = 250
	var sleep: Long = 20
	var rows = 30
	var columns = 40
	var creatures = Set(
		Creature(0, 9),
		Creature(0, 10),
		Creature(0, 11),
		Creature(1, 11),
		Creature(1, 12),
		Creature(5, 6),
		Creature(2, 6),
		Creature(2, 5),
		Creature(1, 6),
		Creature(3, 6)
	)

	private def shift(arg: AtomicInteger, args: Array[String]): String = {
		args(arg.incrementAndGet())
	}

	private def usage() {
		println("Usage: game [options]")
		println("    where options are:")
		println(s" --turns/-t      N   the number of turns, defaults to $turns")
		println(s" --sleep/-s      MS  MS to sleep between turns, defaults to $sleep")
		println(s" --creatures         literal comma-delimited, s-exp style creatures, or an '@'-prefixed file containing same")
		println(s" --rows/-r       N   the number of rows in the game board, defaults to $rows")
		println(s" --columns/-c    N   the number of columsn in the game board, defaults to $columns")
		println(s" --boundaries/-b     flag indicating that game cell boundaries should be printed")
		println(s" --devnull           flag indicating that turn-by-turn state should not be printed")
	}

	val arg = new AtomicInteger(0)
	while (arg.get() < args.length) {
		args(arg.get()) match {
			case "--turns" | "-t" =>
				turns = Integer.parseInt(shift(arg, args))
			case "--sleep" | "-s" =>
				sleep = java.lang.Long.parseLong(shift(arg, args))
			case "--creatures" => {
				val creaturesArg = shift(arg, args)
				creaturesArg.head match {
					case '@' =>
						creatures = Creature.parse(Files.toString(new File(creaturesArg.substring(1)), Charsets.UTF_8))
					case _ =>
						creatures = Creature.parse(creaturesArg)
				}
			}
			case "--rows" | "-r" =>
				rows = Integer.parseInt(shift(arg, args))
			case "--columns" | "-c" =>
				columns = Integer.parseInt(shift(arg, args))
			case "--boundaries" | "-b" =>
				printer = new ConsolePrinter(true)
			case "--devnull" =>
				printer = new DevNullPrinter()
			case "--help" | "-h" =>
				usage()
				sys.exit(0)
			case unknown =>
				println(s"Unexpected option: $unknown")
				usage()
				sys.exit(1)
		}
		arg.incrementAndGet()
	}

	var board = Board(rows, columns, creatures)
	val game = new Game(rows, columns)

	var nanos: Long = 0

	for (turn <- 1 to turns) {
		printer.print(board, turn)

		val start = System.nanoTime()
		board = game.turn(board)
		nanos += (System.nanoTime() - start)

		Thread.sleep(sleep)
	}

	printf("Performance: %.2f ms/turn%n", nanos * 0.000001 / turns)
}