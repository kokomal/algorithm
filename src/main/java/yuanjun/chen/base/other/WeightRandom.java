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

    public static Map<String, Double> servers = new ConcurrentHashMap<String, Double>();
    private static Double sum = 0d;
    static {
        servers.put("192.168.20.101", 4.5d);
        servers.put("192.168.20.102", 2d);
        servers.put("192.168.20.103", 3d);
        servers.put("192.168.20.104", 1d);
    }
    static {
        for (Double i : servers.values()) {
            sum += i;
        }
    }

    /**
     * @Description: 按照权重选取server
     * @param servers
     * @param sum
     * @return String
     */
    public static String selectServer(Map<String, Double> servers, Double sum) {
        if (servers == null || servers.size() == 0)
            return null;
        Set<Map.Entry<String, Double>> entrySet = servers.entrySet();
        Double rand = new Random().nextDouble() * sum;
        for (Map.Entry<String, Double> entry : entrySet) {
            rand -= entry.getValue();
            if (rand <= 0d) {
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
