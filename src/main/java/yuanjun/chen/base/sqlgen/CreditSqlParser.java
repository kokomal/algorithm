package yuanjun.chen.base.sqlgen;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class CreditSqlParser {
    private static final String LEFT_BR = "(";
    private static final String COMMA = ",";
    private static final String QUOTA = "\"";
    private static final String ENTER = "\n";
    private static final String DOMAIN = "credit";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DOMAIN + ".";
    private static final String VARCHAR = " VARCHAR(5000)";
    private static final String PRIMARY = " NOT NULL PRIMARY KEY";

    /*
     * filename为先前导出的credit.log字段文件 destfilename为输出的文件名 isAppend为是否续在已有重名文件后面
     */
    public static void parseLogToSQL(String filename, String destfilename, boolean isAppend) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(filename);
        StringBuilder mainSql = new StringBuilder();
        try (FileReader reader = new FileReader(url.getFile()); BufferedReader br = new BufferedReader(reader)) {
            do {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                if (!line.isEmpty()) {
                    StringBuilder pack = new StringBuilder(ENTER + CREATE_TABLE + line + ENTER + LEFT_BR + ENTER + "rowkey" + VARCHAR + PRIMARY
                            + COMMA + ENTER);
                    do {
                        String next = br.readLine();
                        if (next == null || next.isEmpty()) { // 下一行为空，说明到底了
                            int pos = pack.toString().lastIndexOf(',');
                            pack = new StringBuilder(pack.substring(0, pos));
                            pack.append(ENTER + ");" + ENTER);
                            break;
                        }
                        pack.append(QUOTA).append(next).append(QUOTA).append(VARCHAR).append(COMMA).append(ENTER);

                    } while (true);
                    mainSql.append(pack);
                }
            } while (true);
        } catch (IOException e) {
        }
        System.out.println(mainSql);
        ByteBuffer header = ByteBuffer.wrap(("-- GENERATE SQL @" + (new Date().toString())).getBytes("utf-8"));
        ByteBuffer body = ByteBuffer.wrap(mainSql.toString().getBytes("utf-8"));

        ByteBuffer[] bufferArray = {header, body};
        FileOutputStream fos = new FileOutputStream(destfilename, isAppend); // non appendable
        FileChannel writeChannle = fos.getChannel();
        writeChannle.write(bufferArray);
        fos.close();
        writeChannle.close();
    }

    public static void main(String[] args) throws Exception {
        parseLogToSQL("credit.log.txt", "D:\\xml\\en.sql", false);
    }
}
