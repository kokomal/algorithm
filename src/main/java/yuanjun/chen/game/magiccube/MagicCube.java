/**
 * @Title: MagicCube.java
 * @Package: yuanjun.chen.game.magiccube
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年1月9日 下午3:01:54
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.game.magiccube;

/**
 * @ClassName: MagicCube
 * @Description: 魔方demo类
 * @author: 陈元俊
 * @date: 2019年1月9日 下午3:01:54
 */
public class MagicCube {

    public static enum Axis {
        X, Y, Z
    }

    public static enum Color{
        WHITE, GREEN, BLUE, RED, YELLOW, ORANGE
    }
    
    private int n;
    
    private Color[][][] colors;

    public MagicCube(int n) {
        this.n = n;
        this.colors = new Color[6][n][n];
        for (int i = 0; i < 6; i ++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    this.colors[i][j][k] = Color.values()[i];
                }
            }
        }
    }

    /*
     *          ^ Z
     *          |
     *          |
     *          ---------> X
     *         /
     *        / 
     *       L Y
     * dir means rotate by which axis 
     * nPhases means how many phases(90 degrees) have been rotated
     * nPhases could be negative, which stands for counter-clockwise, likewise positive stands for clockwise
     * level stands for the level number of the layer at specified axis, can be only [1, n] and 1 from axis sharp 
     */
    public void rotate(Axis axis, int nPhases, int level) {
        nPhases = nPhases < 0 ? (4 + (nPhases % 4)) % 4 : nPhases;
    }
    
    public void show() {
        System.out.println("***********************");
        for (int i = 0; i < 6; i ++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    System.out.print(this.colors[i][j][k] + "|");
                }
                System.out.println();
            }
            System.out.println("----------------------");
        }
        System.out.println("***********************");
    }
    
    
    public static void rotateOneFace(Color[][] raw, int nPhases) {
        
    }
    
    
    public static void main(String[] args) {
        MagicCube cube = new MagicCube(3);
        // cube.show();
        int k = -19;
        System.out.println((4 + k % 4) % 4);
    }
}
