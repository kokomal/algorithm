package yuanjun.chen.advanced.datastructure.graph;

public class TREENODE {
    public static final String BLACK = "BLACK";
    public static final String WHITE = "WHITE";
    public static final String GRAY = "GRAY";
    
    public static final String BLUE = "BLUE";
    public static final String RED = "RED";

    public int idx;
    public String color = WHITE;
    public String pre = null;
    public int d = Integer.MAX_VALUE;

    public TREENODE(int idx) {
        super();
        this.idx = idx;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idx;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TREENODE other = (TREENODE) obj;
        if (idx != other.idx)
            return false;
        return true;
    }
}
