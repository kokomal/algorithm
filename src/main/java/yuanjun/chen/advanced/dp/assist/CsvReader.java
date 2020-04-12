package yuanjun.chen.advanced.dp.assist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<String> readCsv(String filename) {
        List<String> lines = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(filename);
        try (FileReader reader = new FileReader(url.getFile()); BufferedReader br = new BufferedReader(reader)) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
        }
        return lines;
    }

}
