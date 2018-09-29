package yuanjun.chen.game.twentyfour.inner;

public enum Level {
    ADULT_100(9, "100以内"), ADULT_90(8, "90以内"), ADULT_80(7, "80以内"), ADULT_70(6, "70以内"), ADULT_60(5,
            "60以内"), ADULT_50(4, "50以内"), ADULT_40(3, "40以内"), ADULT_30(2, "30以内"), ADULT(1, "20以内"), CHILD(0, "10以内");
    private int level;
    private String lvlDesc;

    public int getLevel() {
        return level;
    }

    public String getDesc() {
        return lvlDesc;
    }

    Level(int i, String desc) {
        this.level = i;
        this.lvlDesc = desc;
    }

    public static Level getByI(int i) {
        for (Level x : Level.values()) {
            if (x.getLevel() == i) {
                return x;
            }
        }
        return CHILD;
    }

    public Level inc() {
        int inc = this.level + 1;
        return getByI(inc);
    }

    public Level dec() {
        int inc = this.level - 1;
        return getByI(inc);
    }
}
