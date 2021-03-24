package yuanjun.chen.base.disjoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*优化为字符串类型和hashmap*/
public class RefinedDisjointSet {
    private Map<String, String> parent; // parent
    private Map<String, Integer> rank; // rank

    public void makeSet(Set<String> initKeys) {
        parent = new HashMap<String, String>(initKeys.size());
        rank = new HashMap<String, Integer>(initKeys.size());
        initKeys.stream().forEach(e -> {
            parent.put(e, e);
            rank.put(e, 0);
        });
    }

    public String findSet(String x) {
        if (!parent.get(x).equals(x)) {
            parent.put(x, findSet(parent.get(x)));
        }
        return parent.get(x);
    }

    private void link(String x, String y) {
        if (x.equals(y)) return;
        if (rank.get(x) > rank.get(y)) {
            parent.put(y, x);
        } else {
            parent.put(x, y);
            if (rank.get(x) == rank.get(y)) {
                rank.put(y, rank.get(y) + 1);
            }
        }
    }

    public void union(String x, String y) {
        this.link(findSet(x), findSet(y));
    }

    public void printout() {
        System.out.println(parent);
        System.out.println(rank);
        System.out.println("-----");
    }

    public static void main(String[] args) {
        RefinedDisjointSet rs = new RefinedDisjointSet();
        rs.makeSet(Stream.of("a", "b", "c", "d", "e", "f", "g", "h", "i").collect(Collectors.toSet()));

        rs.union("b", "d");
        rs.printout();

        rs.union("a", "c");
        rs.printout();

        rs.union("a", "b");
        rs.printout();

        rs.union("b", "c");
        rs.printout();

        rs.union("e", "f");
        rs.union("e", "g");
        rs.union("h", "i");
        rs.printout();
    }
}
