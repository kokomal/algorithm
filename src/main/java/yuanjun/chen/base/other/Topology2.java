package yuanjun.chen.base.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*LEETCODE 423*/
public class Topology2 {
	private static String[] sources = new String[] { "zero", "one", "two", "three", "four", "five", "six", "seven",
			"eight", "nine", "ten" };

	private static Map<Character, Integer> rec = new HashMap<>();
	private static Set<String> legal = new HashSet<>();
	private static final HashMap<String, Integer> reflect = new HashMap<>();
	static {
		int i = 0;
		for (String src : sources) {
			reflect.put(src, i);
			i++;
		}
	}

	private static class SEQ {
		public char x;
		public String full;

		public SEQ(char x, String full) {
			super();
			this.x = x;
			this.full = full;
		}

		@Override
		public String toString() {
			return full;
		}
	}

	public static List<SEQ> genSeq() {
		List<SEQ> res = new ArrayList<>();
		for (String src : sources) {
			legal.add(src);
			for (char c : src.toCharArray()) {
				if (rec.containsKey(c)) {
					rec.put(c, rec.get(c) + 1);
				} else {
					rec.put(c, 1);
				}
			}
		}
		while (true) {
			char x = findOne();
			if (x == (char) -1)
				break;
			String rem = "";
			for (String l : legal) {
				if (l.contains(new String(new char[] { x }))) {
					rem = l;
					break;
				}
			}
			res.add(new SEQ(x, rem));
			legal.remove(rem);
			for (char y : rem.toCharArray()) {
				rec.put(y, rec.get(y) - 1);
				if (rec.get(y) == 0)
					rec.remove(y);
			}
		}
		return res;
	}

	private static char findOne() {
		while (!rec.isEmpty()) {
			for (char x : rec.keySet()) {
				if (rec.get(x) == 1) {
					return x;
				}
			}
		}
		return (char) -1;
	}

	public static String originalDigits(String s) {
		List<SEQ> seqs = genSeq();
		System.out.println(seqs);
		TreeMap<Integer, Integer> count = new TreeMap<>();
		rec.clear();
		for (char c : s.toCharArray()) {
			if (rec.containsKey(c)) {
				rec.put(c, rec.get(c) + 1);
			} else {
				rec.put(c, 1);
			}
		}
		for (SEQ sq : seqs) { // g, eight
			if (rec.containsKey(sq.x)) {
				int cc = rec.get(sq.x);
				for (char rm : sq.full.toCharArray()) {
					rec.put(rm, rec.get(rm) - cc);
					if (rec.get(rm) == 0)
						rec.remove(rm);
				}
				count.put(reflect.get(sq.full), cc);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int k : count.keySet()) {
			for (int i = 0; i < count.get(k); i++) {
				sb.append(Integer.toString(k));
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(originalDigits("owoztneoerttwowo"));
	}
}
