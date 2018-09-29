package yuanjun.chen.game.guessNumber.inner;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GuessGame {

	private static final String SUCCESS = "4A0B";
	private static final String ILL_FORMAT = "ill-format";
	private static final String QUIT = "q";

	private static final String WELCOME = "Welcome & Start to Guess [press q to quit]---\n";

	private int len = 4;
	private int span = 10; // a-h
	private char startWith = '0';
	private int times = 0;
	private String targ;
	private String read;

	public GuessGame(int len, int span, char startWith) {
		super();
		this.len = len;
		this.span = span;
		this.startWith = startWith;
		this.times = 0;
		this.targ = "";
		this.read = "";
	}

	public void test() {
		targ = getRandomString(len, span, startWith);
		System.out.println("for try--" + targ);
	}

	public void play() {
		System.out.println("The Game Rule is: Guess " + len + " numbers, from " + startWith + " to "
				+ String.valueOf((char) (startWith + span - 1)));
		welcome();
		targ = getRandomString(len, span, startWith);

		while (true) {
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			read = scan.nextLine().trim();
			String ans = guessCore(this.targ, this.read);

			if (QUIT.equalsIgnoreCase(ans)) {
				System.out.println("goodbye!");
				break;
			} else if (SUCCESS.equalsIgnoreCase(ans)) {
				times++;
				System.out.println("4A0B, you win @" + times + " times");
				times = 0;
				welcome();
				targ = getRandomString(len, span, startWith);
			} else if (ILL_FORMAT.equalsIgnoreCase(ans)) {
				System.out.println("invalid input, try again!");
			} else {
				times++;
				System.out.print("Your guess result = " + ans + " @"
						+ numAddTh(times) + " try.\n");
			}
		}
	}

	private static String numAddTh(int times) {
		String tail = "";
		int tl = Math.abs(times % 10);
		if (tl == 1) {
			tail = "st";
		} else if (tl == 2) {
			tail = "nd";
		} else if (tl == 3) {
			tail = "rd";
		} else {
			tail = "th";
		}
		return times + tail;
	}

	private static void welcome() {
		System.out.print(WELCOME);
	}

	private static String guessCore(String targ, String read) {
		if (QUIT.equalsIgnoreCase(read)) {
			return QUIT;
		} else if (targ.equalsIgnoreCase(read)) {
			return SUCCESS;
		} else if (read.length() != targ.length()) {
			return ILL_FORMAT;
		} else {
			int Acount = 0;
			int Bcount = 0;
			read = read.toUpperCase();
			for (int i = 0; i < targ.length(); i++) {
				char ai = read.charAt(i);
				if (targ.charAt(i) == ai) {
					Acount++;
				} else if (targ.indexOf(String.valueOf(ai)) != -1) {
					Bcount++;
				}
			}
			return Acount + "A" + Bcount + "B";
		}
	}

	private static int getRandom(int count) {
		return (int) Math.round(Math.random() * count);
	}

	private static String getRandomString(int length, int span, char start) {
		StringBuffer sb = new StringBuffer();
		Set<Integer> s = new HashSet<>();
		for (int i = 0; i < length; i++) {
			while (true) {
				int idx = getRandom(span) % span + start;
				if (s.contains(idx)) {
					continue;
				} else {
					s.add(idx);
					char x = (char) (idx);
					sb.append(x);
					break;
				}
			}
		}
		return sb.toString();
	}
}
