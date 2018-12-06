/**
 * @Title: Jdk8Tester.java
 * @Package: yuanjun.chen.performance.jdk8
 * @Description: 测试jdk8的流特性和lambda函数
 * @author: 陈元俊
 * @date: 2018年12月6日 上午8:57:27
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.performance.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @ClassName: Jdk8Tester
 * @Description: 感谢IBM Developer社区的介绍
 * @author: 陈元俊
 * @date: 2018年12月6日 上午8:57:27
 */
public class Jdk8Tester {
    @Test
    public void testStreamIter() { // forEach属于terminal谓词
        IntStream.of(new int[] {1, 2, 3}).forEach(System.out::println); // IntStream为定制的int类型的stream，避免拆箱的性能问题
        IntStream.range(1, 3).forEach(System.out::println); // [1,3)
        IntStream.rangeClosed(1, 3).forEach(System.out::println); // [1,3]
    }

    @Test
    public void testStreamConstruct() {
        String[] strArray = new String[] {"a", "b", "c"};
        Stream<String> stream = Stream.of(strArray);
        String[] strArray1 = stream.toArray(String[]::new);
        System.out.println("String[] Fetch from stream, " + Arrays.toString(strArray1));

        stream = Stream.of(strArray); // stream一次只能用一次
        List<String> list1 = stream.collect(Collectors.toList());
        System.out.println("List<String> Fetch from stream again, " + list1);

        List<String> list = Arrays.asList(strArray);
        stream = list.stream(); // list内嵌stream
        String str = stream.collect(Collectors.joining()).toString();
        System.out.println("Combination is " + str); // join
    }

    @Test
    public void testStreamMap() { // map-->collect
        List<String> wordList = new ArrayList<>();
        wordList.add("china");
        wordList.add("india");
        wordList.add("russia");
        List<String> output = wordList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("After mapping, " + output);
    }

    @Test
    public void testStreamMap2() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().map(n -> n * n).collect(Collectors.toList());
        System.out.println("using lambda --> " + squareNums);
        List<Integer> squareNums2 = nums.stream().map(Jdk8Tester::square).collect(Collectors.toList());
        System.out.println("using self-def -->" + squareNums2);
    }

    private static int square(int x) { // 自定义函数
        return x * x;
    }

    @Test
    public void testStreamFlatMap() { // flatMap用于将流的元素全部归并
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
        Integer[] combinated = outputStream.toArray(Integer[]::new);
        System.out.println("After combination, " + Arrays.toString(combinated));
    }

    @Test
    public void testStreamFlatMapPerson() { // 更加详细的解释
        List<NoblePerson> persons = new ArrayList<>();
        NoblePerson A = new NoblePerson();
        A.name = "Joseph Statlin";
        A.hobbyList = Arrays.asList(new Hobby[] {Hobby.Play, Hobby.Eat});
        persons.add(A);
        NoblePerson B = new NoblePerson();
        B.name = "Winston Churchill";
        B.hobbyList = Arrays.asList(new Hobby[] {Hobby.Sleep, Hobby.Eat, Hobby.Drink});
        persons.add(B);
        Set<Hobby> hobbySet = persons.parallelStream().flatMap(p -> p.getHobbyList().stream())
                .collect(Collectors.toCollection(() -> new TreeSet<Hobby>((h1, h2) -> h1.name().compareTo(h2.name()))));
        System.out.println(hobbySet); // 这里用flatMap把每一个元素的list的stream合体成一个，然后组成一个treeset
    }

    @Test
    public void testStreamFilter() { // filter将不符合条件的成员筛除，但元数据仍然存在
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
        System.out.println("even ones: " + Arrays.toString(evens));
        System.out.println("while old data remains, " + Arrays.toString(sixNums));

        // 分词，去掉标点
        List<String> raws =
                Arrays.asList(new String[] {"haha it is a good place", "new founded land!", "old continent?"});
        String REGEXP = "\\W+";
        List<String> output = raws.stream().flatMap(line -> Stream.of(line.split(REGEXP)))
                .filter(word -> word.length() > 0).collect(Collectors.toList());
        System.out.println(output);
    }

    @Test
    public void testForEach() { // forEach只能使用1次，peek可以多次
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        nums.stream().forEach(p -> System.out.println(p));
        // nums.stream().forEach(System::out::println); // WRONG!!
        List<?> ll = Stream.of("one", "two", "three", "four").filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e)).map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e)).collect(Collectors.toList());
        System.out.println(ll);
    }

    @Test
    public void testOptional() {
        String strA = " abcd ", strB = null;
        print(strA);
        print("");
        print(strB);
        System.out.println("Len of " + strA + "=" + getLength(strA));
        System.out.println("Len of empStr =" + getLength(""));
        System.out.println("Len of " + strB + "=" + getLength(strB));
    }

    public static void print(String text) {
        // Java 8
        Optional.ofNullable(text).ifPresent(System.out::println);
        // Pre-Java 8
        // if (text != null) {
        // System.out.println(text);
        // }
    }

    public static int getLength(String text) {
        // Java 8
        return Optional.ofNullable(text).map(String::length).orElse(-1);
        // Pre-Java 8
        // return if (text != null) ? text.length() : -1;
    };

    @Test
    public void testReduce() {
        List<Integer> ll = Arrays.asList(new Integer[] {1, 2, 3, 4, 5});
        Integer sum = ll.stream().reduce(0, Integer::sum);// reduce需要有一个初始化的值来进行计算或者比较
        System.out.println("Sum of " + ll + " is " + sum);

        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println("ABCD should return " + concat);

        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println("Min of (-1.5, 1.0, -3.0, -2.0) should be " + minValue);

        // 求和，sumValue = 10, 无起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get(); // 也可以不需要！
        System.out.println("Without init val, sumValue is " + sumValue);

        // 过滤，字符串连接，concat = "ace" 留意不带初始参数可能会是null，需要Optional进行简易的非空判断
        Optional<String> concat2 =
                Stream.of("a", "B", "c", "D", "e", "F").filter(x -> x.compareTo("z") > 0).reduce(String::concat);
        System.out.println("concat is " + (concat2.isPresent() ? concat2.get() : "EMPTY"));
    }

    @Test
    public void testSkip() {
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        // getname本身已经在limit的管控下了
        List<String> personList2 = persons.stream().map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2); // 4-10

        persons.clear();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList3 = persons.stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).limit(2)
                .collect(Collectors.toList());
        System.out.println(personList3);
    }

    @Test
    public void testSkip2() { // 先短路limit，然后sort，不管怎么样，limit都是比较易错的地方，需要非常慎重！
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .collect(Collectors.toList());
        System.out.println(personList2);
    }
    
    @Test
    public void testMax() {
        List<String> raws =
                Arrays.asList(new String[] {"haha it is a good place", "new founded land!", "old continent?"});
        int longest = raws.stream().mapToInt(String::length).max().getAsInt();
        System.out.println(longest);
    }
    
    @Test
    public void testDistinct() { // tolower在前，distinct在后，因此IS和Is全部成为is；Big和BIG全部成为big
        List<String> raws = Arrays.asList(new String[] {"China Big Country", "Chistmas Is Coming", "Xmas Is BIG"});
        List<String> words = raws.stream().flatMap(line -> Stream.of(line.split(" "))).filter(word -> word.length() > 0)
                .map(String::toLowerCase).distinct().sorted().collect(Collectors.toList());
        System.out.println(words);
    }
    
    @Test
    public void testMatch() {
        List<String> raws =
                Arrays.asList(new String[] {"China is a Big Country", "Chistmas Is Coming to ChinA", "Xmas Is BIG"});
        boolean isAllChina = raws.stream().map(String::toLowerCase).allMatch(p -> p.contains("china"));
        System.out.println("All are China? " + isAllChina);
        boolean isBigInvolved = raws.stream().map(String::toLowerCase).anyMatch(p -> p.contains("big"));
        System.out.println("Is big involved? " + isBigInvolved);
    }
    
    @Test
    public void testGenerateStream() {
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
        limit(10).forEach(System.out::println);
    }
    
    @Test
    public void testSelfGen() {
        Stream.generate(new PersonSupplier()).limit(10)
                .forEach(p -> System.out.println(p.getName() + ", " + p.getNo()));
    }
    
    @Test
    public void testGenArray() { // 生成等差数列
        Stream.iterate(0, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));
    }
    
    @Test
    public void testPythagoras() {
        int a = 9;
        IntStream.rangeClosed(1, 100) // 相当于切片一样 里面的元素是1-100
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                .map(b -> new int[] {a, b, (int) Math.sqrt(a * a + b * b)})
                .forEach(r -> System.out.println("a=" + r[0] + ",b=" + r[1] + ",c=" + r[2]));
    }

    @Test
    public void testPythagoras2() { // 非常精妙的Stream使用，首先遍历a，然后遍历比a大的b，过滤符合条件的c
        int limit = 100;
        Stream<int[]> gain = IntStream.range(1, limit).boxed()
                .flatMap(a -> IntStream.range(a + 1, limit).filter(b -> qualified(a, b, limit))// 判断斜边是否为整数
                        .mapToObj(b -> new int[] {a, b, (int) Math.sqrt(a * a + b * b)}));
        gain.forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));
        // 作者：墙角的牵牛花
    }
    
    private static boolean qualified(int a, int b, int x) {
        double res= Math.sqrt(a * a + b * b);
        return (res <= x) && (res % 1 == 0);
    }
    
    private class PersonSupplier implements Supplier<Person> {
        private int index = 0;
        @Override
        public Person get() {
            index++;
            return new Person(index, "StormTestUser" + index);
        }
    }
}
