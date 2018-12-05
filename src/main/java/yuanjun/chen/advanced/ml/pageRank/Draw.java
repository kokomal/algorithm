package yuanjun.chen.advanced.ml.pageRank;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * code by 邦柳 画图类
 */
public class Draw {
    public Graphics2D graphics;
    public BufferedImage image;

    // 构造函数
    public Draw() {
        image = new BufferedImage(DrawSet.Width, DrawSet.Height, BufferedImage.TYPE_INT_BGR);
        graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font(DrawSet.Font, Font.BOLD, DrawSet.FontSize);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, DrawSet.Width, DrawSet.Height);
        graphics.setColor(Color.BLACK);
        float thick = DrawSet.LineTrick;
        graphics.setStroke(new BasicStroke(thick, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
    }

    // 基于起点x1.y1,终点,x2,y2画一条线
    public void drawLine(double x1, double y1, double x2, double y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        graphics.draw(line);
    }

    // 绘制点
    public void drawPoint(int order, double x, double y) {
        float xx = (float) ((float) (x - DrawSet.CenterX) * DrawSet.FontDistance + DrawSet.CenterX);
        float yy = (float) ((float) (y - DrawSet.CenterY) * DrawSet.FontDistance + DrawSet.CenterY);
        graphics.drawString(String.valueOf(order), xx, yy);
    }

    // 绘制点，重载
    public void drawPoint(int order, double pageRank, double x, double y) {
        float xx = (float) ((float) (x - DrawSet.CenterX) * DrawSet.FontDistance + DrawSet.CenterX);
        float yy = (float) ((float) (y - DrawSet.CenterY) * DrawSet.FontDistance + DrawSet.CenterY);
        graphics.drawString(String.valueOf(order), xx, yy);
        DecimalFormat df = new DecimalFormat("#0.00");
        xx = (float) ((float) (x - DrawSet.CenterX) * DrawSet.PageRankDistance + DrawSet.CenterX);
        yy = (float) ((float) (y - DrawSet.CenterY) * DrawSet.PageRankDistance + DrawSet.CenterY);
        graphics.drawString(df.format(pageRank), xx, yy);
    }

    // 执行画图的初始化
    public void createImage(String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 参数配置完毕，执行画图
    public void over() {
        graphics.dispose();
        createImage(DrawSet.WebPicture);
    }

    // 将需要画图的参数传入
    public void drawPic(int len, PageRankEntity page) {
        System.out.println("get into draw");
        double diameter = 2000;
        double w = 3000;
        double h = 3000;
        double x[] = new double[len + 1];
        double y[] = new double[len + 1];

        for (int i = 0; i < len; i++) {
            double ii = 0.36 * i;
            x[i] = Math.cos(ii) * diameter + w;
            y[i] = Math.sin(ii) * diameter + h;
        }

        for (int i = 0; i < len; i++) {
            for (int j1 = 0; j1 < len; j1++) {
                if (page.num[i][j1] != 0) {
                    if (i != j1) {
                        drawLine(x[i], y[i], x[j1], y[j1]);
                    }
                }
            }
        }
        for (int i = 0; i < len; i++) {
            int point = i;
            drawPoint(i + 1, page.v[i], x[point], y[point]);
            // 找到那一点的坐标标上i+1
        }
        over();
    }

    int kk = 10;
    
public static void main(String[] args) {

    Draw d = new Draw();
    System.out.println(d.kk);
}
}
