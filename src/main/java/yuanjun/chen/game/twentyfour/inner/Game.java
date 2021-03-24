package yuanjun.chen.game.twentyfour.inner;

import java.util.*;

public class Game {
	private static double Minimum = 0.000001;
	private static final String RIGHT_BRACKET = ")";
	private static final String LEFT_BRACKET = "(";
	private static final String REV_DIVIDE = "//";
	private static final String MULTIPLE = "*";
	private static final String DIVIDE = "/";
	private static final String SUBTRACT = "-";
	private static final String ADD = "+";
	private List<Double> list = new ArrayList<>();
	private List<Integer> intlist = new ArrayList<>();
	/** Private ResAndRepr res = new ResAndRepr();. */

	private Set<String> solutions = new HashSet<>();

	public Set<String> getSolutions() {
		return solutions;
	}

	public void setSolutions(Set<String> solutions) {
		this.solutions = solutions;
	}

	public List<Integer> getIntlist() {
	    Collections.sort(intlist);
		return intlist;
	}

	public void setIntlist(List<Integer> intlist) {
		this.intlist = intlist;
	}

	public List<Double> getList() {
		return list;
	}

	public void setList(List<Double> list) {
		this.list = list;
	}

	public Game(double a, double b, double c, double d) {
		init(a, b, c, d);
	}

	public void init(double a, double b, double c, double d) {
		list.clear();
		intlist.clear();
		solutions.clear();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		intlist.add((int) a);
		intlist.add((int) b);
		intlist.add((int) c);
		intlist.add((int) d);
	}

	public void reset(double a, double b, double c, double d) {
		init(a, b, c, d);
	}

	private static Set<ResAndRepr> allPossible(double a, double b) {
		Set<ResAndRepr> ress = new HashSet<>();

		ResAndRepr re1 = new ResAndRepr();
		re1.setRes(a + b);
		re1.setRepr(repr((int) a, ADD, (int) b));
		ress.add(re1);

		ResAndRepr re2 = new ResAndRepr();
		re2.setRes(Math.abs(a - b));
		re2.setRepr(a > b
				? repr((int) a, SUBTRACT, (int) b)
				: repr((int) b, SUBTRACT, (int) a));
		ress.add(re2);

		ResAndRepr re3 = new ResAndRepr();
		re3.setRes(a * b);
		re3.setRepr(repr((int) a, MULTIPLE, (int) b));
		ress.add(re3);

		if (Math.abs(b) > Minimum) {
			ResAndRepr re4 = new ResAndRepr();
			re4.setRes(a / b);
			re4.setRepr(repr((int) a, DIVIDE, (int) b));
			ress.add(re4);
		}

		if (Math.abs(a) > Minimum) {
			ResAndRepr re5 = new ResAndRepr();
			re5.setRes(b / a);
			re5.setRepr(repr((int) b, DIVIDE, (int) a));
			ress.add(re5);
		}
		return ress;
	}

	private static String repr(Object a, String opr, Object b) {
		StringBuilder sb = new StringBuilder(a.toString());
		sb.append(opr);
		sb.append(b);
		return sb.toString();
	}

	private static String reprBracket(Object a, String opr, Object b) {
		StringBuilder sb = new StringBuilder(LEFT_BRACKET);
		sb.append(a);
		sb.append(opr);
		sb.append(b);
		sb.append(RIGHT_BRACKET);
		return sb.toString();
	}

	public ResAndRepr checkPossible(double targ, List<Double> list,
			boolean dbgFlag) {
		if (targ < 0) {
			return reject();
		}
		ResAndRepr res = new ResAndRepr();
		int k = list.size();
		if (k == 2) {
			return can_A_and_B_Make_Targ(list.get(0), list.get(1), targ,
					dbgFlag);
		}
		for (int i = 0; i < k; i++) {
			double select = list.get(i);
			List<Double> subList = fetchSub(list, i);

			double targ0 = select - targ;
			ResAndRepr resX0 = checkPossible(targ0, subList, dbgFlag);
			if (resX0.isExecutable()) {
				res.setExecutable(true);
				res.setRepr(smartBracket(k, (int) select, SUBTRACT,
						resX0.getRepr()));
				if (k != 4) {
					debugOut(targ, res.getRepr(), dbgFlag);
					return res;
				} else {
					debugOut(targ, res.getRepr(), dbgFlag);
					debugLine(dbgFlag);
					packSolution(res.getRepr(), dbgFlag);
				}
			}

			double targ1 = targ - select;
			ResAndRepr resX1 = checkPossible(targ1, subList, dbgFlag);
			if (resX1.isExecutable()) {
				res.setExecutable(true);
				res.setRepr(
						smartBracket(k, (int) select, ADD, resX1.getRepr()));
				if (k != 4) {
					debugOut(targ, res.getRepr(), dbgFlag);
					return res;
				} else {
					debugOut(targ, res.getRepr(), dbgFlag);
					debugLine(dbgFlag);
					packSolution(res.getRepr(), dbgFlag);
				}
			}

			double targ2 = targ + select;
			ResAndRepr resX2 = checkPossible(targ2, subList, dbgFlag);
			if (resX2.isExecutable()) {
				res.setExecutable(true);
				res.setRepr(smartBracket(k, resX2.getRepr(), SUBTRACT,
						(int) select));
				if (k != 4) {
					debugOut(targ, res.getRepr(), dbgFlag);
					return res;
				} else {
					debugOut(targ, res.getRepr(), dbgFlag);
					debugLine(dbgFlag);
					packSolution(res.getRepr(), dbgFlag);
				}
			}

			if (Math.abs(select) > Minimum) {
				double targ3 = targ * select;
				ResAndRepr resX3 = checkPossible(targ3, subList, dbgFlag);
				if (resX3.isExecutable()) {
					res.setExecutable(true);
					res.setRepr(smartBracket(k, resX3.getRepr(), DIVIDE,
							(int) select));
					if (k != 4) {
						debugOut(targ, res.getRepr(), dbgFlag);
						return res;
					} else {
						debugOut(targ, res.getRepr(), dbgFlag);
						debugLine(dbgFlag);
						packSolution(res.getRepr(), dbgFlag);
					}
				}
			}

			if (Math.abs(select) > Minimum) {
				double targ4 = targ / select;
				ResAndRepr resX4 = checkPossible(targ4, subList, dbgFlag);
				if (resX4.isExecutable()) {
					res.setExecutable(true);
					res.setRepr(smartBracket(k, (int) select, MULTIPLE,
							resX4.getRepr()));
					if (k != 4) {
						debugOut(targ, res.getRepr(), dbgFlag);
						return res;
					} else {
						debugOut(targ, res.getRepr(), dbgFlag);
						debugLine(dbgFlag);
						packSolution(res.getRepr(), dbgFlag);
					}
				}
			}

			if (Math.abs(targ) > Minimum && Math
					.abs(select) > Minimum) { /* ��Ҫ����0��Ȼ����0/x=0 �����Ƴ�x = 0/0 */
				double targ5 = select / targ;
				ResAndRepr resX5 = checkPossible(targ5, subList, dbgFlag);
				if (resX5.isExecutable()) {
					res.setExecutable(true);
					res.setRepr(smartBracket(k, (int) select, DIVIDE,
							resX5.getRepr()));
					if (k != 4) {
						debugOut(targ, res.getRepr(), dbgFlag);
						return res;
					} else {
						debugOut(targ, res.getRepr(), dbgFlag);
						debugLine(dbgFlag);
						packSolution(res.getRepr(), dbgFlag);
					}
				}
			}
		}

		if (k == 4) {
			ResAndRepr res1;
			// case A--0,1 vs 2,3
			res1 = check4ForTwoBracket(list, 0, 1, 2, 3, targ, res, dbgFlag);
			if (res1 != null) {
				packSolution(res1.getRepr(), dbgFlag);
				res = res1;
			}
			// case B--0,2 vs 1,3
			res1 = check4ForTwoBracket(list, 0, 2, 1, 3, targ, res, dbgFlag);
			if (res1 != null) {
				packSolution(res1.getRepr(), dbgFlag);
				res = res1;
			}
			// case C--0,3 vs 1,2
			res1 = check4ForTwoBracket(list, 0, 3, 1, 2, targ, res, dbgFlag);
			if (res1 != null) {
				packSolution(res1.getRepr(), dbgFlag);
				res = res1;
			}
		}
		return res;
	}

	private void packSolution(String resRepr, boolean dbgFlag) {
		if (dbgFlag) {
			solutions.add(resRepr);
		}
	}

	private static void debugLine(boolean dbgFlag) {
		if (dbgFlag) {
			System.out.println("----------------------------------------------");
		}
	}

	private static String debugOut(double targ, String resRepr,
			boolean dbgFlag) {
		String tg;
		tg = Math.abs(targ - (int) targ) < Minimum
				? Integer.toString((int) targ)
				: Double.toString(targ);
		String re = "----" + resRepr + " makes " + tg;
		if (dbgFlag) {
			System.out.println(re);
		}
		return re;
	}

	private static ResAndRepr check4ForTwoBracket(List<Double> list, int n1,
			int n2, int m1, int m2, double targ, ResAndRepr res,
			boolean dbgFlag) {
		Set<ResAndRepr> set1, set2;
		set1 = allPossible(list.get(n1), list.get(n2));
		set2 = allPossible(list.get(m1), list.get(m2));
		ResAndRepr res4;
		for (ResAndRepr r1 : set1) {
			for (ResAndRepr r2 : set2) {
				res4 = can_A_and_B_Make_Targ(r1.getRes(), r2.getRes(), targ,
						false);
				if (res4.isExecutable() && targ > 0) { // ���еĸ�����������ת���������Ľⷨ
					debugOut(r1.getRes(), r1.getRepr(), dbgFlag);
					debugOut(r2.getRes(), r2.getRepr(), dbgFlag);
					if (REV_DIVIDE.equals(res4.getOpr())) {
						debugOut(res4.getRes(),
								LEFT_BRACKET + r2.getRepr() + ")/("
										+ r1.getRepr() + RIGHT_BRACKET,
								dbgFlag);
					} else {
						debugOut(res4.getRes(),
								LEFT_BRACKET + r1.getRepr() + RIGHT_BRACKET
										+ res4.getOpr() + LEFT_BRACKET
										+ r2.getRepr() + RIGHT_BRACKET,
								dbgFlag);
					}
					debugLine(dbgFlag);
					return parseRes(r1.getRepr(), res4.getOpr(), r2.getRepr());
				}
			}
		}
		return null;
	}

	private static ResAndRepr parseRes(Object r1Repr, String res4Opr,
			Object r2Repr) {
		ResAndRepr res = new ResAndRepr();
		res.setExecutable(true);
		if (REV_DIVIDE.equals(res4Opr)) {
			res.setRepr(reprBracket(r2Repr, ")/(", r1Repr));
		} else {
			res.setRepr(repr(
					repr(LEFT_BRACKET, r1Repr.toString(), RIGHT_BRACKET),
					res4Opr,
					repr(LEFT_BRACKET, r2Repr.toString(), RIGHT_BRACKET)));
		}
		return res;
	}

	private static String smartBracket(int k, Object ob1, String opr,
			Object ob2) {
		return k == 4 ? repr(ob1, opr, ob2) : reprBracket(ob1, opr, ob2);
	}

	private static List<Double> fetchSub(List<Double> list2, int i) {
		List<Double> list = new ArrayList<>();
		for (int j = 0; j < list2.size(); j++) {
			if (i != j) {
				list.add(list2.get(j));
			}
		}
		return list;
	}

	public static ResAndRepr can_A_and_B_Make_Targ(double a, double b,
			double targ, boolean dbgFlag) {
		if (targ < 0 || a < 0 || b < 0) { // ���еĸ�����������ת���������Ľⷨ
			return reject();
		}

		ResAndRepr res = new ResAndRepr();
		res.setExecutable(true);

		if (Math.abs(a + b - targ) < Minimum) {
			res.setOpr(ADD);
			res.setRepr(reprBracket((int) a, ADD, (int) b));
		} else if (Math.abs(a - b - targ) < Minimum) {
			res.setOpr(SUBTRACT);
			res.setRepr(reprBracket((int) a, SUBTRACT, (int) b));
		} else if (Math.abs(a * b - targ) < Minimum) {
			res.setOpr(MULTIPLE);
			res.setRepr(reprBracket((int) a, MULTIPLE, (int) b));
		} else if (Math.abs(b) > Minimum
				&& Math.abs(a / b - targ) < Minimum) {
			res.setOpr(DIVIDE);
			res.setRepr(reprBracket((int) a, DIVIDE, (int) b));
		} else if (Math.abs(a) > Minimum
				&& Math.abs(b / a - targ) < Minimum) {
			res.setOpr(REV_DIVIDE);
			res.setRepr(reprBracket((int) b, DIVIDE, (int) a));
		} else {
			return reject();
		}
		res.setRes(targ);
		debugOut(targ, res.getRepr(), dbgFlag);
		return res;
	}

	private static ResAndRepr reject() {
		ResAndRepr res = new ResAndRepr();
		res.setExecutable(false);
		res.setRepr("");
		return res;
	}

	public ResAndRepr check(double i, boolean dbgFlag) {
		return checkPossible(i, this.list, dbgFlag);
	}
}
