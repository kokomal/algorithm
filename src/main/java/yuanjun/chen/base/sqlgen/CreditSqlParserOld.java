package yuanjun.chen.base.sqlgen;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class CreditSqlParserOld {
	private static final String COMMA = ",";
	private static final String ENTER = "\n";
	private static final String DOMAIN = "hbase";
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DOMAIN + ".";
	private static final String VARCHAR = " VARCHAR(5000)";
	private static final String PRIMARY = " NOT NULL PRIMARY KEY";

	/*
	 * filename为先前导出的credit.log字段文件 
	 * destfilename为输出的文件名 
	 * isAppend为是否续在已有重名文件后面
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
					String pack = ENTER + CREATE_TABLE + line + ENTER + "(" + ENTER;
					boolean isFirst = true;
					do {
						String next = br.readLine();
						if (next == null || next.isEmpty()) { // 下一行为空，说明到底了
							int pos = pack.lastIndexOf(COMMA);
							pack = pack.substring(0, pos);
							pack += ENTER + ");" + ENTER;
							break;
						}
						if (isFirst) { // 是不是第一个ID字段?如果是，则要加primary key声明
							pack += next + VARCHAR + PRIMARY + COMMA + ENTER;
							isFirst = false;
						} else {
							pack += next + VARCHAR + COMMA + ENTER;
						}

					} while (true);
					mainSql.append(pack);
				}
			} while (true);
		} catch (IOException e) {
		}
		System.out.println(mainSql);
		ByteBuffer header = ByteBuffer.wrap(("-- GENERATE SQL @" + (new Date().toString())).getBytes("utf-8"));
		ByteBuffer body = ByteBuffer.wrap(mainSql.toString().getBytes("utf-8"));

		ByteBuffer[] bufferArray = { header, body };
		FileOutputStream fos = new FileOutputStream(destfilename, isAppend); // non appendable
		FileChannel writeChannle = fos.getChannel();
		writeChannle.write(bufferArray);
		fos.close();
		writeChannle.close();
	}

	public static void main(String[] args) throws Exception {
		parseLogToSQL("credit.log.txt", "d://gen.sql", false);
	}
}
