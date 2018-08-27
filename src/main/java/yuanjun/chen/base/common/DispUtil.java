package yuanjun.chen.base.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DispUtil {
    private static final Logger logger = LogManager.getLogger(DispUtil.class);

    public static void split(int len, char x) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(x);
        }
        logger.info(sb.toString());
    }

    public static void embed(int len, char x, String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(x);
        }
        sb.append(word);
        for (int i = 0; i < len; i++) {
            sb.append(x);
        }
        logger.info(sb.toString());
    }

    public static String splitOne(int len, char x) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(x);
        }
        return sb.toString();
    }

    public static void showMatrix(Integer[][] matrix) {
        for (Integer[] line : matrix) {
            StringBuilder sb = new StringBuilder();
            sb.append("|").append(line[0]);
            for (int i = 1; i < line.length; i++) {
                sb.append(",").append(line[i]);
            }
            sb.append("|");
            logger.info(sb.toString());
            sb = null;
        }
    }
    
    public static <T extends Object> void showMatrixForCopy(final T[][] matrix) {
        StringBuilder sb = new StringBuilder("\n");
        logger.info("Matrix len = " + matrix.length + " * " + matrix[0].length);
        for (T[] line : matrix) {
            sb.append(line[0]);
            for (int i = 1; i < line.length; i++) {
                sb.append(",").append(line[i]);
            }
            sb.append("\n");
        }
        logger.info(sb.toString());
    }
}
