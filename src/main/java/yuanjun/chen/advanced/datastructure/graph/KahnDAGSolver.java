package yuanjun.chen.advanced.datastructure.graph;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static yuanjun.chen.advanced.datastructure.graph.KahnDAGSolver.DAGQueueNode.QueueCommandType.*;

/*
 * A.B.Kahn 1962提出的DAG的环侦测和topological sort算法实现，对比DFS，其环侦测实现简单，而且更加适合并行化改造
 * 约定1. adjs存放外射的相邻矩阵adjs[from]=List[to]，孤立节点也有Key，但Value为[]
 * 约定2. indegrees存放入度，其生命周期为init-->sort，排序后为全0，在侦测阶段不受影响
 * 约定3. withDependencies由于业务原因，为K所依赖的前置节点，也就是<to, [froms]>这样的结构
 * */
public class KahnDAGSolver {
    public static class DAGCycleException extends Exception {

    }

    public static class DAGQueueNode<T> {
        public enum QueueCommandType {
            NORMAL, STOP, TERMINATE
        }

        DAGQueueNode.QueueCommandType queueCommandType;
        String nodeKey;
        T data;

        private DAGQueueNode() {
        }

        private static <T> DAGQueueNode<T> build(DAGQueueNode.QueueCommandType type, String nodeKey, T data) {
            DAGQueueNode<T> dqn = new DAGQueueNode<>();
            dqn.queueCommandType = type;
            dqn.nodeKey = nodeKey;
            dqn.data = data;
            return dqn;
        }

        public static <T> DAGQueueNode<T> buildNormalPill(String nodeKey, T data) {
            return build(NORMAL, nodeKey, data);
        }

        public static <T> DAGQueueNode<T> genTerminatePill() {
            return build(TERMINATE, null, null);
        }
    }

    private Map<String, Set<String>> adjs = new ConcurrentHashMap<>();
    private Map<String, Integer> indegrees = new ConcurrentHashMap<>();
    private boolean hasCycles = false;
    private int millisPerLoop = 0;
    private static final int MAX_CONCURRENT = 32;

    // 带各个节点和前置依赖关系的构造，需要考虑无依赖节点
    public KahnDAGSolver(Map<String, Collection<String>> nodesWithDeps) {
        if (nodesWithDeps != null && !nodesWithDeps.isEmpty()) {
            nodesWithDeps.forEach((to, froms) -> froms.forEach(from -> addEdge(from, to)));
        }
        // visit adjs to generate indegrees
        adjs.keySet().forEach(k -> indegrees.put(k, 0));
        adjs.forEach((K, V) -> {
            V.forEach(v -> {
                indegrees.put(v, indegrees.get(v) + 1);
            });
        });
        if (indegrees.size() < 100) {
            millisPerLoop = 100;
        } else if (indegrees.size() < 1000) {
            millisPerLoop = 200;
        } else {
            millisPerLoop = 400;
        }
    }

    private void addEdge(String from, String to) {
        safeInit(from, to);
        adjs.get(from).add(to);
    }

    private void safeInit(String... keys) {
        for (String key : keys) {
            if (!adjs.containsKey(key)) {
                adjs.put(key, new HashSet<>());
            }
        }
    }

    // 纯环侦测无需并发工具协助
    public boolean checkHasCycle() {
        Map<String, Integer> tempIndegrees = new HashMap<>(indegrees);
        Queue<String> queueOfVertices = tempIndegrees.entrySet().stream().filter(e -> e.getValue() == 0).map(Map.Entry::getKey).collect(Collectors.toCollection(LinkedList::new));
        // 获得初始化的入度为0的queue
        int nVerticesVisited = 0;
        while (!queueOfVertices.isEmpty()) {
            String budgetCode = queueOfVertices.poll();
            decreaseIndegrees(queueOfVertices, budgetCode, tempIndegrees);
            nVerticesVisited++;
        }
        hasCycles = nVerticesVisited != adjs.size();
        return hasCycles;
    }

    public KahnDAGSolver withoutCycles() throws DAGCycleException {
        if (checkHasCycle()) throw new DAGCycleException();
        return this;
    }

    public void sequentialSolve() {
        Queue<String> queueOfVertices = indegrees.entrySet().stream().filter(e -> e.getValue() == 0).map(Map.Entry::getKey).collect(Collectors.toCollection(LinkedList::new));
        // 获得初始化的入度为0的queue
        while (!queueOfVertices.isEmpty()) {
            String budgetCode = queueOfVertices.poll();
            // 外调任务，只有执行成功才可以走下一步
            System.out.printf("SEQ CALLING SPARKLING FOR %s BEGINS\n", budgetCode);
            try {
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
            }
            System.out.printf("SEQ CALLING SPARKLING FOR %s ENDS\n", budgetCode);
            decreaseIndegrees(queueOfVertices, budgetCode, indegrees);
        }
    }

    public void concurrentSolve() {
        LinkedBlockingQueue<String> queueOfVertices = indegrees.entrySet().stream().filter(e -> e.getValue() == 0).map(Map.Entry::getKey).collect(Collectors.toCollection(LinkedBlockingQueue::new));
        AtomicInteger virtualSizeOfQueue = new AtomicInteger(queueOfVertices.size());
        AtomicInteger nRunningTasks = new AtomicInteger(0); // nRunningTasks <= virtualSizeOfQueue
        AtomicBoolean errorFlag = new AtomicBoolean(false); // 初始化错误flag为false
        ExecutorService cc = Executors.newCachedThreadPool(); // TODO 将来用标准的线程池代替
        // long start = System.currentTimeMillis(); // TODO 将来对执行时间进行监控
        // 初始化
        // 外调任务，只有执行成功才可以走下一步，这里初始化限流，避免一开始就把大量任务丢到线程池
        IntStream.range(0, Math.min(queueOfVertices.size(), MAX_CONCURRENT)).mapToObj(i -> queueOfVertices.poll()).forEach(budgetCode -> {
            if (budgetCode != null) {
                cc.submit(new SparklingCalcTask(budgetCode, queueOfVertices, virtualSizeOfQueue, nRunningTasks, errorFlag));
            } else {
                System.out.println("INIT InDegree Queue Empty.");
            }
        });
        System.out.println("NOW virtualSizeOfQueue" + virtualSizeOfQueue.get());
        System.out.println("NOW queueOfVertices" + queueOfVertices.size());
        System.out.println("NOW nRunningTasks" + nRunningTasks.get());
        // 正式执行
        while (!errorFlag.get() && virtualSizeOfQueue.get() > 0) {
            int currRunningTasks = nRunningTasks.get();
            if (currRunningTasks >= MAX_CONCURRENT) {
                System.out.println("Too Many UndoneTasks Of " + currRunningTasks); // 入口流量控制，限流sparkling
                sleepForOneLoop(millisPerLoop);
            } else {
                String budgetCode = queueOfVertices.poll();
                // async外调任务
                if (budgetCode != null) {
                    nRunningTasks.incrementAndGet(); // 预先++
                    cc.submit(new SparklingCalcTask(budgetCode, queueOfVertices, virtualSizeOfQueue, nRunningTasks, errorFlag));
                } else {
                    System.out.println("Busy, As InDegree Queue Is Empty******"); // queue为空，说明thread在途运行，没有feed新的对象到queue
                    sleepForOneLoop(millisPerLoop);
                }
            }
        }
        try {
            cc.shutdown();
            cc.awaitTermination(100L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }

    }

    // 用固定的线程池，简化资源控制
    public void concurrentFixedSolve() {
        DAGSession cache = new DAGSession();
        LinkedBlockingQueue<DAGQueueNode<DAGSession>> queueOfVertices = indegrees.entrySet().stream().filter(e -> e.getValue() == 0)
                .map(e -> DAGQueueNode.buildNormalPill(e.getKey(), cache)).collect(Collectors.toCollection(LinkedBlockingQueue::new));
        AtomicInteger virtualSizeOfQueue = new AtomicInteger(queueOfVertices.size());
        AtomicBoolean errorFlag = new AtomicBoolean(false); // 初始化错误flag为false
        ExecutorService cc = Executors.newFixedThreadPool(16); // 固定的线程池
        IntStream.range(0, 16).forEach(budgetCode -> {
            cc.submit(new SparklingPoisonPillCalcTask<>(queueOfVertices, virtualSizeOfQueue, errorFlag, cache));
        });
        while (virtualSizeOfQueue.get() > 0) {
            if (errorFlag.get()) {
                System.out.println("Main procedure exits due to error!");
                break;
            }
        }
        // normal terminates
        if (!errorFlag.get()) {
            IntStream.range(0, 16).forEach(budgetCode -> {
                queueOfVertices.add(DAGQueueNode.genTerminatePill()); // send poisonous pills to every healthy one
            });
        }
        try {
            cc.shutdown();
            cc.awaitTermination(100L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    private static void sleepForOneLoop(int millis) {
        try {
            Thread.sleep(new Random().nextInt(millis));
        } catch (InterruptedException e) {
        }
    }

    private void decreaseIndegrees(Queue<String> queueOfVertices, String zero, Map<String, Integer> indegrees) {
        adjs.get(zero).forEach(toVertex -> {
            int reducedInd = indegrees.get(toVertex) - 1; // 关联到的to节点入度--
            indegrees.put(toVertex, reducedInd);
            if (reducedInd == 0) { // 有入度为0则加入Queue
                queueOfVertices.add(toVertex);
            }
        });
    }

    private class SparklingCalcTask implements Runnable {
        private final String budgetCode;
        private final Queue<String> queueOfVertices;
        private final AtomicInteger virtualSizeOfQueue; // 与queue的实际size基本同步,但是先加后扣，因此在生命周期内不会为0，从而避免全局退出
        private final AtomicInteger nRunningTasks; // 任意task进入+1，退出-1
        private final AtomicBoolean errFlag; // 任意task进入+1，退出-1

        private SparklingCalcTask(String budgetCode, Queue<String> queueOfVertices, AtomicInteger virtualSizeOfQueue, AtomicInteger nRunningTasks, AtomicBoolean errFlag) {
            this.budgetCode = budgetCode;
            this.queueOfVertices = queueOfVertices;
            this.virtualSizeOfQueue = virtualSizeOfQueue;
            this.nRunningTasks = nRunningTasks;
            this.errFlag = errFlag;
        }

        @Override
        public void run() {
            System.out.printf("CONCUR CALLING SPARKLING FOR %s BEGINS\n", budgetCode);
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
            }
            int rand = new Random().nextInt(1000);
            if (rand > 998) {
                errFlag.set(true);
                System.out.printf("SPARKLING FOR %s POPS with %d^^^^^^^^^^^^^^^^^^^^\n", budgetCode, rand);
                return;
            }
            System.out.printf("CONCUR CALLING SPARKLING FOR %s ENDS\n", budgetCode);
            adjs.get(budgetCode).forEach(toVertex -> {
                int reducedInd = indegrees.get(toVertex) - 1; // 关联到的to节点入度--
                indegrees.put(toVertex, reducedInd);
                if (reducedInd == 0) { // 有入度为0则加入Queue
                    queueOfVertices.add(toVertex);
                    virtualSizeOfQueue.incrementAndGet(); // 同步virtualSizeOfQueue的大小
                }
            });
            virtualSizeOfQueue.decrementAndGet(); // queue的任务计数扣掉自己
            nRunningTasks.decrementAndGet(); // 运行中的任务扣除
        }
    }

    interface ICacheable {
        Object fetch(String key);

        boolean put(String key, Object value);
    }

    public static class DAGSession implements ICacheable {
        private final Map<String, Object> sessionMap = new ConcurrentHashMap<>();

        @Override
        public Object fetch(String key) {
            return sessionMap.get(key);
        }

        @Override
        public boolean put(String key, Object value) {
            sessionMap.put(key, value);
            return true;
        }
    }

    /*
     * 毒丸方式结束线程
     * errorFlag的方式强制退出
     *
     * */
    private class SparklingPoisonPillCalcTask<T extends ICacheable> implements Runnable {
        private final Queue<DAGQueueNode<T>> queueOfVertices;
        private final AtomicInteger virtualSizeOfQueue; // 与queue的实际size基本同步,但是先加后扣，因此在生命周期内不会为0，从而避免全局退出
        private final AtomicBoolean errFlag;
        private T cache;

        private SparklingPoisonPillCalcTask(Queue<DAGQueueNode<T>> queueOfVertices, AtomicInteger virtualSizeOfQueue, AtomicBoolean errFlag, T cache) {
            this.queueOfVertices = queueOfVertices;
            this.virtualSizeOfQueue = virtualSizeOfQueue;
            this.errFlag = errFlag;
            this.cache = cache;
        }

        @Override
        public void run() {
            while (!errFlag.get()) { // 直接控制error强行退出
                DAGQueueNode<T> vertexPopped = queueOfVertices.poll();
                if (vertexPopped == null) { // 空队列，说明还在执行
                } else if (STOP.equals(vertexPopped.queueCommandType)) {
                    // 异常退出
                    System.out.println("ERROR STOP!!!");
                    return;
                } else if (TERMINATE.equals(vertexPopped.queueCommandType)) {
                    // 正常退出
                    System.out.println("NORMAL TERMINATE!!!");
                    return;
                } else {
                    assert vertexPopped.nodeKey != null;
                    // 正常执行
                    System.out.printf("CONCUR CALLING SPARKLING FOR %s BEGINS\n", vertexPopped.nodeKey);
                    try {
                        if ("A".equals(vertexPopped.data)) {
                            Thread.sleep(new Random().nextInt(3000));
                        }
                        Thread.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                    }
                    int rand = new Random().nextInt(1000);
//                    if (rand > 990) {
//                        errFlag.set(true);
//                        System.out.printf("SPARKLING FOR %s POPS with %d^^^^^^^^^^^^^^^^^^^^\n", vertexPopped, rand);
//                        return;
//                    }
                    System.out.printf("CONCUR CALLING SPARKLING FOR %s ENDS\n", vertexPopped.nodeKey);
                    adjs.get(vertexPopped.nodeKey).forEach(toVertex -> {
                        int reducedInd = indegrees.get(toVertex) - 1; // 关联到的to节点入度--
                        indegrees.put(toVertex, reducedInd);
                        if (reducedInd == 0) { // 有入度为0则加入Queue
                            queueOfVertices.add(DAGQueueNode.buildNormalPill(toVertex, cache));
                            virtualSizeOfQueue.incrementAndGet(); // 同步virtualSizeOfQueue的大小
                        }
                    });
                    virtualSizeOfQueue.decrementAndGet(); // queue的任务计数扣掉自己
                }
            }
            System.out.print("SPARKLING THREAD FORCIBLY EXITS\n");
        }
    }

    public static void main(String[] args) throws DAGCycleException {
        Map<String, Collection<String>> nodesWithDeps = new HashMap<>();
        nodesWithDeps.put("A", Arrays.asList("B", "C"));
        nodesWithDeps.put("B", Arrays.asList("X"));
        List<String> cs = new ArrayList<>();
        cs.add("K");
        IntStream.range(0, 2000).mapToObj(i -> "w" + i).forEach(cs::add);
        nodesWithDeps.put("C", cs);
//        nodesWithDeps.put("V", Arrays.asList("C")); // DANGER!!! LOOP
        List<String> us = new ArrayList<>();
        us.add("Q");
        us.add("V");
        IntStream.range(0, 2000).mapToObj(i -> "u" + i).forEach(us::add);
        nodesWithDeps.put("K", us);
        nodesWithDeps.get("K").addAll(us);
        KahnDAGSolver kahnDAGSolver = new KahnDAGSolver(nodesWithDeps);
        kahnDAGSolver.withoutCycles().concurrentFixedSolve();
        System.out.println("Back to the lab yo!");
    }

}
