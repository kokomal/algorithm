package yuanjun.chen.game.twentyfour;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import yuanjun.chen.game.twentyfour.inner.Game;
import yuanjun.chen.game.twentyfour.inner.Level;
import yuanjun.chen.game.twentyfour.inner.ResAndRepr;

public class Console {
    private static final String SPLITTER_EQUALS = "=================================================================";
    private static final String TIPS =
            "-Press [x] to challenge computer.\n" + "-Press [a] to get answer.\n-Press [+] to level up.\n-Press [-] to "
                    + "level down.\n-Press [c] to continue.\n-Press [q] to quit.";
    private static final String WELCOME = "WELCOME TO %d POINTS!\n" + TIPS;
    private static final String WANNA_CONTINUE = "Do you want to continue?\n" + TIPS;

    public static void main(String[] args) {
        // letsGo();

        // playMe();
        genGames();
    }

    /**   
     * @Title: genGames   
     * @Description: 生成游戏数据      
     * @return: void      
     * @throws   
     */
    private static void genGames() {
        Set<String> ll = new HashSet<>();
        while (ll.size() < 120) {
            Game g = generateRandomPlayableGame(null, 24, Level.CHILD);
            g.check(24.0, false);
            ll.add(g.getIntlist().toString());
        }
        ll.stream().sorted().forEach(System.out::println);
    }

    /**   
     * @Title: playMe   
     * @Description: 游戏主程序      
     * @return: void      
     * @throws   
     */
    private static void playMe() {
        System.out.println("Please input the target value:");
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        String read1 = scan.nextLine().trim();
        int tg = Integer.parseInt(read1);
        System.out.println("What level do you want? Press [c] for child; Press [a] for adult:");
        String read2 = scan.nextLine().trim();
        Level level;
        if ("a".equalsIgnoreCase(read2)) {
            level = Level.ADULT;
        } else {
            level = Level.CHILD;
        }
        System.out.println("Begin to start game for: " + read1 + " and the level is: " + level.getDesc());
        playInteractively((double) tg, level);
    }

    public static void playInteractively(final double targ, Level level) {
        Level localLevel = level;
        String read = "";
        Game g = generateRandomPlayableGame(null, targ, localLevel);

        welcome(targ, read);
        gameBegin(g.getIntlist());
        do {
            @SuppressWarnings("resource")
            Scanner scan = new Scanner(System.in);
            read = scan.nextLine().trim();
            if ("q".equalsIgnoreCase(read)) {
                System.out.println("goodbye!");
                System.exit(0);
                break;
            } else if ("a".equalsIgnoreCase(read)) {
                System.out.println("ANSWERS for " + g.getIntlist() + " are listed below:");
                g.check(targ, true);
                success(targ, g);
            } else if ("c".equalsIgnoreCase(read)) {
                welcome(targ, read);
                g = generateRandomPlayableGame(g, targ, localLevel);
                gameBegin(g.getIntlist());
            } else if ("-".equals(read)) {
                localLevel = localLevel.dec();
                System.out.println("Switching to Level: " + localLevel.getDesc());
                playInteractively(targ, localLevel);
            } else if ("+".equals(read)) {
                localLevel = localLevel.inc();
                System.out.println("Switching to Level: " + localLevel.getDesc());
                playInteractively(targ, localLevel);
            } else if ("x".equalsIgnoreCase(read)) {
                System.out.println("START TO CHALLENGE COMPUTER!\nPlease input four digits separated by \",\":");
                String[] inputs;
                do {
                    read = scan.nextLine().trim();
                    inputs = read.split(",");
                    if (inputs == null || inputs.length != 4) {
                        System.out.println("Please input exactly 4 digits!");
                    } else {
                        try {
                            g.reset(Double.parseDouble(inputs[0]), Double.parseDouble(inputs[1]),
                                    Double.parseDouble(inputs[2]), Double.parseDouble(inputs[3]));
                        } catch (NumberFormatException e) {
                            System.out.println("Illegal digits! please try again!");
                            continue;
                        }
                        break;
                    }
                } while (true);
                System.out.println("COMPUTER has accepted the challenge: " + Arrays.asList(inputs));
                ResAndRepr rex = g.check(targ, true);
                if (rex.isExecutable()) {
                    success(targ, g);
                } else {
                    System.out.println("===For input: " + g.getIntlist() + "NOT SOLVABLE!");
                    System.out.println(SPLITTER_EQUALS);
                    System.out.println(WANNA_CONTINUE);
                }
            }
        } while (true);
    }

    private static void success(final double targ, Game g) {
        System.out.println("===For input: " + g.getIntlist() + " all the solutions are: ");
        for (String re : g.getSolutions()) {
            System.out.println(">>>" + re + " makes " + (int) targ);
        }
        System.out.println(SPLITTER_EQUALS);
        System.out.println(WANNA_CONTINUE);
    }

    private static void welcome(double targ, String read) {
        System.out.println(String.format(WELCOME, (int) targ));
    }

    private static void gameBegin(List<Integer> ll) {
        System.out.println("===For input: " + ll + " the game begins");
    }

    private static Game generateRandomGame(Game g, Level lev) {
        Random rand = new Random();
        int a, b, c, d;
        int lv = lev.getLevel();
        a = rand.nextInt(10 * (lv + 1));
        b = rand.nextInt(10 * (lv + 1));
        c = rand.nextInt(10 * (lv + 1));
        d = rand.nextInt(10 * (lv + 1));

        if (g == null) {
            return new Game(a, b, c, d);
        }
        g.reset(a, b, c, d);
        return g;
    }

    private static Game generateRandomPlayableGame(Game g, double targ, Level lev) {
        do {
            g = generateRandomGame(g, lev); // 不要带0，否则太没意思了
            if (!g.getIntlist().contains(0) && g.check(targ, false).isExecutable()) {
                return g;
            }
        } while (true);
    }

    public static void letsGo() {
        long t1 = System.currentTimeMillis();
        autoPlayRandomly(20, 24.0, Level.ADULT);
        long t2 = System.currentTimeMillis();
        System.out.println("alltogether the time is " + (t2 - t1) + "ms");
    }

    public static void autoPlayRandomly(int times, double targ, Level level) {
        int i = times;
        while (i > 0) {
            Game g = generateRandomGame(null, level);
            gameBegin(g.getIntlist());
            ResAndRepr res = g.check(targ, true);
            if (res.isExecutable()) {
                i--;
                System.out.println("===For input: " + g.getIntlist() + " the solution = " + res.getRepr());
            } else {
                System.out.println("***For input: " + g.getIntlist() + " the solution unsolvable!");
            }
        }
    }
}
