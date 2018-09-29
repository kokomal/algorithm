package yuanjun.chen.base.other;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: WeightRandom
 * @Description: 自己设计的加权负载均衡算法
 * @author: 陈元俊
 * @date: 2018年9月29日 上午11:05:16
 */
public class WeightRandom {

    public static Map<String, Integer> servers = new ConcurrentHashMap<String, Integer>();
    private static Integer sum = 0;
    static {
        servers.put("192.168.20.101", 4);
        servers.put("192.168.20.102", 2);
        servers.put("192.168.20.103", 3);
        servers.put("192.168.20.104", 1);
    }
    static {
        for (Integer i : servers.values()) {
            sum += i;
        }
    }

    /**
     * @Description: 按照权重选取server
     * @param servers
     * @param sum
     * @return String
     */
    public static String selectServer(Map<String, Integer> servers, Integer sum) {
        if (servers == null || servers.size() == 0)
            return null;
        Set<Map.Entry<String, Integer>> entrySet = servers.entrySet();
        Integer rand = new Random().nextInt(sum) + 1;
        for (Map.Entry<String, Integer> entry : entrySet) {
            rand -= entry.getValue();
            if (rand <= 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, Integer> client = new HashMap<String, Integer>();
        String key = null;
        // Integer sum = 0;

        for (int i = 0; i < 10000; i++) {
            key = selectServer(servers, sum);
            if (client.containsKey(key)) {
                client.put(key, client.get(key) + 1);
            } else {
                client.put(key, 1);
            }
        }
        for (String key1 : client.keySet()) {
            System.out.println("Client: " + key1 + " gets " + client.get(key1) + " hits");
        }
    }

}
