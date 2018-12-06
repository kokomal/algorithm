package yuanjun.chen.performance.jdk8;

public class Person {
    public int no;
    private String name;

    public Person(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public String getName() {
        System.out.println(name);
        return name;
    }

    @Override
    public String toString() {
        return "Person [no=" + no + ", name=" + name + "]";
    }

    public int getNo() {
        // TODO Auto-generated method stub
        return no;
    }
}
