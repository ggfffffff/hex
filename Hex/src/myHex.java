import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;

public class myHex extends JFrame implements MouseListener, WindowListener {
    public static Polygon boardSet[][] = new Polygon[9][9];
    // 红色1，蓝色2，无棋子0
    public int hexSet[][] = new int[9][9];
    // 有绿色描边3，有橙色描边4，有白色描边5，没有描边0
    public int hint[][] = new int[9][9];
    // 记录被选中的棋子
    public int ichoosed = -1;
    public int jchoosed = -1;
    public int colorChoosed = -1;
    // 上一步走的棋子的颜色；
    public int lastColor = -1;
    public JDialog test = new JDialog();
    Graphics g;

    public static void main(String[] args) {
        myHex hex = new myHex();
        hex.setVisible(true);
    }

    public myHex() {
        // 绘制主页面
        this.setTitle("myHex");
        this.setPreferredSize(new Dimension(740, 650));
        this.pack();
        // his.setVisible(false);
        this.setLocationRelativeTo(null);
        // 设置背景颜色
        Color backgroundColor = new Color(44, 48, 55);
        this.getContentPane().setBackground(backgroundColor);
        // System.out.println("init");
        // 画笔
        g = this.getGraphics();
        // 添加鼠标监听器
        this.addMouseListener(this);
    }

    public void paint(Graphics g) {
        // /System.out.println("print");
        super.paint(g);
        this.init();
    }

    public void init() {
        // 初始化数组
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                hexSet[i][j] = 0;
                hint[i][j] = 0;
                ichoosed = -1;
                jchoosed = -1;
                colorChoosed = -1;
                lastColor = -1;
            }
        }
        // 画棋盘
        drawBoard(g, boardSet);
        // 画初始棋子
        hexSet[1][7] = 1;
        hexSet[7][1] = 1;
        hexSet[1][1] = 2;
        hexSet[7][7] = 2;

        this.update();
    }

    public void update() {
        // System.out.println("update");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // System.out.println("[" + i + "][" + j + "]=" + hexSetColor[i][j]);
                // 画棋子
                drawHexagon(g, i, j, hexSet[i][j]);
                // 画高亮
                drawHint(g, i, j, hint[i][j]);
            }
        }

        // 判断正负
        drawOverDialog(gameover());
    }

    // 画棋盘
    public void drawBoard(Graphics g, Polygon[][] boardSet) {
        Color hexagonColor = new Color(80, 87, 95);
        g.setColor(hexagonColor);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i % 2 == 0) {
                    int[] xPoints = { 55 + 74 * j, 90 + 74 * j, 90 + 74 * j, 55 + 74 * j, 20 + 74 * j, 20 + 74 * j };
                    int[] yPoints = { 40 + 128 * i / 2, 60 + 128 * i / 2, 100 + 128 * i / 2, 120 + 128 * i / 2,
                            100 + 128 * i / 2, 60 + 128 * i / 2 };
                    Polygon pHex = new Polygon(xPoints, yPoints, 6);
                    g.fillPolygon(pHex);
                    boardSet[i][j] = pHex;
                    // System.out.println(i);
                } else {
                    int[] xPoints = { 92 + 74 * j, 127 + 74 * j, 127 + 74 * j, 92 + 74 * j, 57 + 74 * j, 57 + 74 * j };
                    int[] yPoints = { 104 + 128 * (i - 1) / 2, 124 + 128 * (i - 1) / 2, 164 + 128 * (i - 1) / 2,
                            184 + 128 * (i - 1) / 2,
                            164 + 128 * (i - 1) / 2, 124 + 128 * (i - 1) / 2 };
                    Polygon pHex = new Polygon(xPoints, yPoints, 6);
                    g.fillPolygon(pHex);
                    boardSet[i][j] = pHex;
                }
            }
        }
    }

    // 绝对位置->相对位置
    public int ipos(double xpos, double ypos) {
        int ipos = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (boardSet[i][j].contains(xpos, ypos) == true) {
                    ipos = i;
                }
            }
        }
        return ipos;
    }

    public int jpos(double xpos, double ypos) {
        int jpos = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (boardSet[i][j].contains(xpos, ypos) == true) {
                    jpos = j;
                }
            }
        }
        return jpos;
    }

    // 画棋子
    public void drawHexagon(Graphics g, int i, int j, int color) {
        // System.out.println("i=" + i);
        // System.out.println("j=" + j);
        // System.out.println("draw1");
        if (color == 1) {
            if (i % 2 == 0) {
                hexSet[i][j] = 1;
                Color redHexColor = new Color(189, 54, 56);
                g.setColor(redHexColor);
                int[] xPoints = { 55 + 74 * j, 86 + 74 * j, 86 + 74 * j, 55 + 74 * j, 24 + 74 * j,
                        24 + 74 * j };
                int[] yPoints = { 44 + 128 * i / 2, 62 + 128 * i / 2, 98 + 128 * i / 2, 116 + 128 * i / 2,
                        98 + 128 * i / 2, 62 + 128 * i / 2 };
                Polygon redHex = new Polygon(xPoints, yPoints, 6);
                g.fillPolygon(redHex);
            } else {
                hexSet[i][j] = 1;
                Color redHexColor = new Color(189, 54, 56);
                g.setColor(redHexColor);
                int[] xPoints = { 92 + 74 * j, 123 + 74 * j, 123 + 74 * j, 92 + 74 * j, 61 + 74 * j,
                        61 + 74 * j };
                int[] yPoints = { 108 + 128 * (i - 1) / 2, 126 + 128 * (i - 1) / 2, 162 + 128 * (i - 1) / 2,
                        180 + 128 * (i - 1) / 2,
                        162 + 128 * (i - 1) / 2, 126 + 128 * (i - 1) / 2 };
                Polygon redHex = new Polygon(xPoints, yPoints, 6);
                g.fillPolygon(redHex);
            }
        } else if (color == 2) {
            if (i % 2 == 0) {
                hexSet[i][j] = 2;
                Color blueHexChColor = new Color(37, 129, 172);
                g.setColor(blueHexChColor);
                int[] xPoints = { 55 + 74 * j, 86 + 74 * j, 86 + 74 * j, 55 + 74 * j, 24 + 74 * j,
                        24 + 74 * j };
                int[] yPoints = { 44 + 128 * i / 2, 62 + 128 * i / 2, 98 + 128 * i / 2, 116 + 128 * i / 2,
                        98 + 128 * i / 2, 62 + 128 * i / 2 };
                Polygon blueHex = new Polygon(xPoints, yPoints, 6);
                g.fillPolygon(blueHex);
                // hexSet[i][j] = blueHex;
            } else {
                hexSet[i][j] = 2;
                Color blueHexChColor = new Color(37, 129, 172);
                g.setColor(blueHexChColor);
                int[] xPoints = { 92 + 74 * j, 123 + 74 * j, 123 + 74 * j, 92 + 74 * j, 61 + 74 * j,
                        61 + 74 * j };
                int[] yPoints = { 108 + 128 * (i - 1) / 2, 126 + 128 * (i - 1) / 2, 162 + 128 * (i - 1) / 2,
                        180 + 128 * (i - 1) / 2,
                        162 + 128 * (i - 1) / 2, 126 + 128 * (i - 1) / 2 };
                Polygon blueHex = new Polygon(xPoints, yPoints, 6);
                g.fillPolygon(blueHex);
                // hexSet[i][j] = blueHex;
            }
        } else if (color == 0) {
            if (i % 2 == 0) {
                hexSet[i][j] = 0;
                Color nullColor = new Color(80, 87, 95);
                g.setColor(nullColor);
                int[] xPoints = { 55 + 74 * j, 86 + 74 * j, 86 + 74 * j, 55 + 74 * j, 24 + 74 * j,
                        24 + 74 * j };
                int[] yPoints = { 44 + 128 * i / 2, 62 + 128 * i / 2, 98 + 128 * i / 2, 116 + 128 * i / 2,
                        98 + 128 * i / 2, 62 + 128 * i / 2 };
                Polygon blueHex = new Polygon(xPoints, yPoints, 6);
                g.fillPolygon(blueHex);

            } else {
                hexSet[i][j] = 0;
                Color nullColor = new Color(80, 87, 95);
                g.setColor(nullColor);
                int[] xPoints = { 92 + 74 * j, 123 + 74 * j, 123 + 74 * j, 92 + 74 * j, 61 + 74 * j,
                        61 + 74 * j };
                int[] yPoints = { 108 + 128 * (i - 1) / 2, 126 + 128 * (i - 1) / 2, 162 + 128 * (i - 1) / 2,
                        180 + 128 * (i - 1) / 2,
                        162 + 128 * (i - 1) / 2, 126 + 128 * (i - 1) / 2 };
                Polygon blueHex = new Polygon(xPoints, yPoints, 6);
                g.fillPolygon(blueHex);
                // hexSet[i][j] = blueHex;
            }
        }
    }

    // 设置高亮提示
    public void setHint(int i, int j) {
        // 所在格子白色描边
        if (i >= 0 && i < 9 && j >= 0 && j < 9) {
            hint[i][j] = 5;
            // System.out.println("i=" + i + " j=" + j);
        }
        // 周围一圈无棋子空格绿色描边
        // 左侧
        if (j - 1 >= 0) {
            if (hexSet[i][j - 1] == 0) {
                hint[i][j - 1] = 3;
            }
        }
        // 右侧
        if (j + 1 < 9) {
            if (hexSet[i][j + 1] == 0) {
                hint[i][j + 1] = 3;
            }
        }
        // 左上
        if (i % 2 == 0) {
            if (j - 1 >= 0 && i - 1 >= 0) {
                if (hexSet[i - 1][j - 1] == 0) {
                    hint[i - 1][j - 1] = 3;
                }
            }
        } else {
            if (i - 1 >= 0) {
                if (hexSet[i - 1][j] == 0) {
                    hint[i - 1][j] = 3;
                }
            }
        }
        // 右上
        if (i % 2 == 0) {
            if (i - 1 >= 0) {
                if (hexSet[i - 1][j] == 0) {
                    hint[i - 1][j] = 3;
                }
            }
        } else {
            if (i - 1 >= 0 && j + 1 < 9) {
                if (hexSet[i - 1][j + 1] == 0) {
                    hint[i - 1][j + 1] = 3;
                }
            }
        }
        // 左下
        if (i % 2 == 0) {
            if (i + 1 < 9 && j - 1 >= 0) {
                if (hexSet[i + 1][j - 1] == 0) {
                    hint[i + 1][j - 1] = 3;
                }
            }
        } else {
            if (i + 1 < 9) {
                if (hexSet[i + 1][j] == 0) {
                    hint[i + 1][j] = 3;
                }
            }
        }
        // 右下
        if (i % 2 == 0) {
            if (i + 1 < 9) {
                if (hexSet[i + 1][j] == 0) {
                    hint[i + 1][j] = 3;
                }
            }
        } else {
            if (i + 1 < 9 && j + 1 < 9) {
                if (hexSet[i + 1][j + 1] == 0) {
                    hint[i + 1][j + 1] = 3;
                }
            }
        }

        // 周围两圈各自橙色描边
        // 左侧
        if (j - 2 >= 0) {
            if (hexSet[i][j - 2] == 0) {
                hint[i][j - 2] = 4;
            }
        }
        // 右侧
        if (j + 2 < 9) {
            if (hexSet[i][j + 2] == 0) {
                hint[i][j + 2] = 4;
            }
        }
        // 左上
        if (j - 1 >= 0 && i - 2 >= 0) {
            if (hexSet[i - 2][j - 1] == 0) {
                hint[i - 2][j - 1] = 4;
            }
        }
        if (i % 2 == 0) {
            if (i - 1 >= 0 && j - 2 >= 0) {
                if (hexSet[i - 1][j - 2] == 0) {
                    hint[i - 1][j - 2] = 4;
                }
            }
        } else {
            if (i - 1 >= 0 && j - 1 >= 0) {
                if (hexSet[i - 1][j - 1] == 0) {
                    hint[i - 1][j - 1] = 4;
                }
            }
        }
        // 右上
        if (j + 1 < 9 && i - 2 >= 0) {
            if (hexSet[i - 2][j + 1] == 0) {
                hint[i - 2][j + 1] = 4;
            }
        }
        if (i % 2 == 0) {
            if (i - 1 >= 0 && j + 1 < 9) {
                if (hexSet[i - 1][j + 1] == 0) {
                    hint[i - 1][j + 1] = 4;
                }
            }
        } else {
            if (i - 1 >= 0 && j + 2 < 9) {
                if (hexSet[i - 1][j + 2] == 0) {
                    hint[i - 1][j + 2] = 4;
                }
            }
        }
        // 左下
        if (i + 2 < 9 && j - 1 >= 0) {
            if (hexSet[i + 2][j - 1] == 0) {
                hint[i + 2][j - 1] = 4;
            }
        }
        if (i % 2 == 0) {
            if (i + 1 < 9 && j - 2 >= 0) {
                if (hexSet[i + 1][j - 2] == 0) {
                    hint[i + 1][j - 2] = 4;
                }
            }
        } else {
            if (i + 1 < 9 && j - 1 >= 0) {
                if (hexSet[i + 1][j - 1] == 0) {
                    hint[i + 1][j - 1] = 4;
                }
            }
        }
        // 右下
        if (i + 2 < 9 && j + 1 < 9) {
            if (hexSet[i + 2][j + 1] == 0) {
                hint[i + 2][j + 1] = 4;
            }
        }
        if (i % 2 == 0) {
            if (i + 1 < 9 && j + 1 < 9) {
                if (hexSet[i + 1][j + 1] == 0) {
                    hint[i + 1][j + 1] = 4;
                }
            }
        } else {
            if (i + 1 < 9 && j + 2 < 9) {
                if (hexSet[i + 1][j + 2] == 0) {
                    hint[i + 1][j + 2] = 4;
                }
            }
        }
        // 正上
        if (i - 2 >= 0) {
            if (hexSet[i - 2][j] == 0) {
                hint[i - 2][j] = 4;
            }
        }
        // 正下
        if (i + 2 < 9) {
            if (hexSet[i + 2][j] == 0) {
                hint[i + 2][j] = 4;
            }
        }
    }

    // 画高亮提示
    public void drawHint(Graphics g, int i, int j, int color) {
        if (color == 5) {
            // 所在格子白色描边
            g.setColor(Color.WHITE);
            g.drawPolygon(boardSet[i][j]);

        } else if (color == 3) {
            // 周围一圈无棋子空格绿色描边
            Color green = new Color(91, 165, 56);
            g.setColor(green);
            g.drawPolygon(boardSet[i][j]);
        } else if (color == 4) {
            // 周围两圈无棋子空格橙色描边
            Color orange = new Color(204, 143, 62);
            g.setColor(orange);
            g.drawPolygon(boardSet[i][j]);
        } else if (color == 0) {
            Color nullColor = new Color(80, 87, 95);
            g.setColor(nullColor);
            g.drawPolygon(boardSet[i][j]);
        }
    }

    // 落子在绿色hint的function
    // 原来的不消失，新的产生
    public void greenFun(int i, int j, int color) {
        hexSet[i][j] = color;
        get(i, j, color);
    }

    // 落子在橙色hint的function
    // 原来的消失，新的产生
    public void orangeFun(int i, int j, int color) {
        hexSet[i][j] = color;
        hexSet[ichoosed][jchoosed] = 0;
        get(i, j, color);
    }

    // 捕获操作
    public void get(int i, int j, int color) {
        // 如果是红色棋子
        if (color == 1) {
            // 周围一圈其他颜色格子被捕获
            // 左侧
            if (j - 1 >= 0) {
                if (hexSet[i][j - 1] != 0) {
                    hexSet[i][j - 1] = 1;
                }
            }
            // 右侧
            if (j + 1 < 9) {
                if (hexSet[i][j + 1] != 0) {
                    hexSet[i][j + 1] = 1;
                }
            }
            // 左上
            if (i % 2 == 0) {
                if (j - 1 >= 0 && i - 1 >= 0) {
                    if (hexSet[i - 1][j - 1] != 0) {
                        hexSet[i - 1][j - 1] = 1;
                    }
                }
            } else {
                if (i - 1 >= 0) {
                    if (hexSet[i - 1][j] != 0) {
                        hexSet[i - 1][j] = 1;
                    }
                }
            }
            // 右上
            if (i % 2 == 0) {
                if (i - 1 >= 0) {
                    if (hexSet[i - 1][j] != 0) {
                        hexSet[i - 1][j] = 1;
                    }
                }
            } else {
                if (i - 1 >= 0 && j + 1 < 9) {
                    if (hexSet[i - 1][j + 1] != 0) {
                        hexSet[i - 1][j + 1] = 1;
                    }
                }
            }
            // 左下
            if (i % 2 == 0) {
                if (i + 1 < 9 && j - 1 >= 0) {
                    if (hexSet[i + 1][j - 1] != 0) {
                        hexSet[i + 1][j - 1] = 1;
                    }
                }
            } else {
                if (i + 1 < 9) {
                    if (hexSet[i + 1][j] != 0) {
                        hexSet[i + 1][j] = 1;
                    }
                }
            }
            // 右下
            if (i % 2 == 0) {
                if (i + 1 < 9) {
                    if (hexSet[i + 1][j] != 0) {
                        hexSet[i + 1][j] = 1;
                    }
                }
            } else {
                if (i + 1 < 9 && j + 1 < 9) {
                    if (hexSet[i + 1][j + 1] != 0) {
                        hexSet[i + 1][j + 1] = 1;
                    }
                }
            }
        }

        // 如果是蓝色格子
        else if (color == 2) {
            // 周围一圈其他颜色格子被捕获
            // 左侧
            if (j - 1 >= 0) {
                if (hexSet[i][j - 1] != 0) {
                    hexSet[i][j - 1] = 2;
                }
            }
            // 右侧
            if (j + 1 < 9) {
                if (hexSet[i][j + 1] != 0) {
                    hexSet[i][j + 1] = 2;
                }
            }
            // 左上
            if (i % 2 == 0) {
                if (j - 1 >= 0 && i - 1 >= 0) {
                    if (hexSet[i - 1][j - 1] != 0) {
                        hexSet[i - 1][j - 1] = 2;
                    }
                }
            } else {
                if (i - 1 >= 0) {
                    if (hexSet[i - 1][j] != 0) {
                        hexSet[i - 1][j] = 2;
                    }
                }
            }
            // 右上
            if (i % 2 == 0) {
                if (i - 1 >= 0) {
                    if (hexSet[i - 1][j] != 0) {
                        hexSet[i - 1][j] = 2;
                    }
                }
            } else {
                if (i - 1 >= 0 && j + 1 < 9) {
                    if (hexSet[i - 1][j + 1] != 0) {
                        hexSet[i - 1][j + 1] = 2;
                    }
                }
            }
            // 左下
            if (i % 2 == 0) {
                if (i + 1 < 9 && j - 1 >= 0) {
                    if (hexSet[i + 1][j - 1] != 0) {
                        hexSet[i + 1][j - 1] = 2;
                    }
                }
            } else {
                if (i + 1 < 9) {
                    if (hexSet[i + 1][j] != 0) {
                        hexSet[i + 1][j] = 2;
                    }
                }
            }
            // 右下
            if (i % 2 == 0) {
                if (i + 1 < 9) {
                    if (hexSet[i + 1][j] != 0) {
                        hexSet[i + 1][j] = 2;
                    }
                }
            } else {
                if (i + 1 < 9 && j + 1 < 9) {
                    if (hexSet[i + 1][j + 1] != 0) {
                        hexSet[i + 1][j + 1] = 2;
                    }
                }
            }
        }

    }

    // 判断该棋子是否能move
    public boolean move(int i, int j, int color) {
        boolean moveFlag = false;
        // 所在格子白色描边
        if (i >= 0 && i < 9 && j > 0 && j <= 9) {
            hint[i][j] = 5;
            // System.out.println("i=" + i + " j=" + j);
        }
        // 周围一圈无棋子空格绿色描边
        // 左侧
        if (j - 1 >= 0) {
            if (hexSet[i][j - 1] == 0) {
                return true;
            }
        }
        // 右侧
        if (j + 1 < 9) {
            if (hexSet[i][j + 1] == 0) {
                return true;
            }
        }
        // 左上
        if (i % 2 == 0) {
            if (j - 1 >= 0 && i - 1 >= 0) {
                if (hexSet[i - 1][j - 1] == 0) {
                    return true;
                }
            }
        } else {
            if (i - 1 >= 0) {
                if (hexSet[i - 1][j] == 0) {
                    return true;
                }
            }
        }
        // 右上
        if (i % 2 == 0) {
            if (i - 1 >= 0) {
                if (hexSet[i - 1][j] == 0) {
                    return true;
                }
            }
        } else {
            if (i - 1 >= 0 && j + 1 < 9) {
                if (hexSet[i - 1][j + 1] == 0) {
                    return true;
                }
            }
        }
        // 左下
        if (i % 2 == 0) {
            if (i + 1 < 9 && j - 1 >= 0) {
                if (hexSet[i + 1][j - 1] == 0) {
                    return true;
                }
            }
        } else {
            if (i + 1 < 9) {
                if (hexSet[i + 1][j] == 0) {
                    return true;
                }
            }
        }
        // 右下
        if (i % 2 == 0) {
            if (i + 1 < 9) {
                if (hexSet[i + 1][j] == 0) {
                    return true;
                }
            }
        } else {
            if (i + 1 < 9 && j + 1 < 9) {
                if (hexSet[i + 1][j + 1] == 0) {
                    return true;
                }
            }
        }

        // 周围两圈各自橙色描边
        // 左侧
        if (j - 2 >= 0) {
            if (hexSet[i][j - 2] == 0) {
                return true;
            }
        }
        // 右侧
        if (j + 2 < 9) {
            if (hexSet[i][j + 2] == 0) {
                return true;
            }
        }
        // 左上
        if (j - 1 >= 0 && i - 2 >= 0) {
            if (hexSet[i - 2][j - 1] == 0) {
                return true;
            }
        }
        if (i % 2 == 0) {
            if (i - 1 >= 0 && j - 2 >= 0) {
                if (hexSet[i - 1][j - 2] == 0) {
                    return true;
                }
            }
        } else {
            if (i - 1 >= 0 && j - 1 >= 0) {
                if (hexSet[i - 1][j - 1] == 0) {
                    return true;
                }
            }
        }
        // 右上
        if (j + 1 < 9 && i - 2 >= 0) {
            if (hexSet[i - 2][j + 1] == 0) {
                return true;
            }
        }
        if (i % 2 == 0) {
            if (i - 1 >= 0 && j + 1 < 9) {
                if (hexSet[i - 1][j + 1] == 0) {
                    return true;
                }
            }
        } else {
            if (i - 1 >= 0 && j + 2 < 9) {
                if (hexSet[i - 1][j + 2] == 0) {
                    return true;
                }
            }
        }
        // 左下
        if (i + 2 < 9 && j - 1 >= 0) {
            if (hexSet[i + 2][j - 1] == 0) {
                return true;
            }
        }
        if (i % 2 == 0) {
            if (i + 1 < 9 && j - 2 >= 0) {
                if (hexSet[i + 1][j - 2] == 0) {
                    return true;
                }
            }
        } else {
            if (i + 1 < 9 && j - 1 >= 0) {
                if (hexSet[i + 1][j - 1] == 0) {
                    return true;
                }
            }
        }
        // 右下
        if (i + 2 < 9 && j + 1 < 9) {
            if (hexSet[i + 2][j + 1] == 0) {
                return true;
            }
        }
        if (i % 2 == 0) {
            if (i + 1 < 9 && j + 1 < 9) {
                if (hexSet[i + 1][j + 1] == 0) {
                    return true;
                }
            }
        } else {
            if (i + 1 < 9 && j + 2 < 9) {
                if (hexSet[i + 1][j + 2] == 0) {
                    return true;
                }
            }
        }
        // 正上
        if (i - 2 >= 0) {
            if (hexSet[i - 2][j] == 0) {
                return true;
            }
        }
        // 正下
        if (i + 2 < 9) {
            if (hexSet[i + 2][j] == 0) {
                return true;
            }
        }
        return moveFlag;
    }

    // 判断是否游戏结束
    public int gameover() {
        // 红1，蓝2，平3
        int red = 0;
        int blue = 0;
        int redNotMove = 0;
        int blueNotMove = 0;

        // 棋盘充满
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (hexSet[i][j] == 1) {
                    red++;
                    if (move(i, j, 1) == false) {
                        redNotMove++;
                    }
                } else if (hexSet[i][j] == 2) {
                    blue++;
                    if (move(i, j, 2) == false) {
                        blueNotMove++;
                    }
                }
            }
        }
        // System.out.println("redNotMove=" + redNotMove);
        // System.out.println("blueNotMove=" + blueNotMove);

        // System.out.println("red=" + red);
        // System.out.println("blue=" + blue);

        // 棋盘已满
        if (red + blue == 81) {
            if (blue > red) {
                return 2;
            } else if (blue < red) {
                return 1;
            }
        }

        // 棋盘未充满但某方棋子被全部捕获
        if (red == 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (hexSet[i][j] == 0) {
                        hexSet[i][j] = 2;
                    }
                }
            }
            update();
            return 2;
        }
        if (blue == 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (hexSet[i][j] == 0) {
                        hexSet[i][j] = 1;
                    }
                }
            }
            update();
            return 1;
        }

        // 棋盘未充满但某方棋子无法行动
        if (blueNotMove == blue && blueNotMove != 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (hexSet[i][j] == 0) {
                        hexSet[i][j] = 1;
                    }
                }
            }
            update();
            return 1;
        }
        if (redNotMove == red && redNotMove != 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (hexSet[i][j] == 0) {
                        hexSet[i][j] = 2;
                    }
                }
            }
            update();
            return 2;
        }
        return 0;
    }

    // 弹出结束窗口
    public void drawOverDialog(int winner) {
        JDialog gameoverDialog = new JDialog(this, "Game Over");
        gameoverDialog.setSize(200, 140);
        gameoverDialog.setLocationRelativeTo(null);
        if (winner == 1) {
            JLabel text = new JLabel("红方胜利！", JLabel.CENTER);
            gameoverDialog.add(text);
            gameoverDialog.setVisible(true);
        } else if (winner == 2) {
            JLabel text = new JLabel("蓝方胜利！", JLabel.CENTER);
            gameoverDialog.add(text);
            gameoverDialog.setVisible(true);
        } else if (winner == 3) {
            JLabel text = new JLabel("两方平手！", JLabel.CENTER);
            gameoverDialog.add(text);
            gameoverDialog.setVisible(true);
        }
        // 添加弹窗监听器
        gameoverDialog.addWindowListener((WindowListener) this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // 鼠标行为
    public void mouseReleased(MouseEvent e) {
        int ipos = ipos((double) e.getX(), (double) e.getY());
        int jpos = jpos((double) e.getX(), (double) e.getY());
        // 要求选择的格子在棋盘上
        if (ipos >= 0 && ipos < 9
                && jpos >= 0 && jpos < 9) {
            // 如果选在了有棋子的格子
            // 要求选与上一步不同色的棋子
            if (hexSet[ipos][jpos] != 0 && hexSet[ipos][jpos] != lastColor) {
                // 先将之前选中的格子周围的hint删去
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (hint[i][j] == 3 || hint[i][j] == 4 || hint[i][j] == 5) {
                            hint[i][j] = 0;
                        }
                    }
                }
                setHint(ipos, jpos);
                ichoosed = ipos;
                jchoosed = jpos;
                colorChoosed = hexSet[ipos][jpos];
            }

            // 如果选在了没有棋子但可以走的格子
            // 可以走means既有hint，又符合落子顺序
            else if (hexSet[ipos][jpos] == 0) {
                // 如果是绿色hint
                if (hint[ipos][jpos] == 3) {
                    lastColor = colorChoosed;
                    greenFun(ipos, jpos, colorChoosed);
                }
                // 如果是橙色hint
                else if (hint[ipos][jpos] == 4) {
                    lastColor = colorChoosed;
                    orangeFun(ipos, jpos, colorChoosed);
                }
                // 走完之后把hint删去
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (hint[i][j] == 3 || hint[i][j] == 4 || hint[i][j] == 5) {
                            hint[i][j] = 0;
                        }
                    }
                }
            }
        }

        // 更新画布
        update();
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    // 关闭弹窗后初始化
    @Override
    public void windowClosing(WindowEvent e) {
        // System.out.println("close");
        paint(g);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

}