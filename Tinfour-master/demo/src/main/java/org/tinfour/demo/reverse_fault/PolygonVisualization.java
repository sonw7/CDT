package org.tinfour.demo.reverse_fault;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
//简单多边形的结果的可视化
public class PolygonVisualization extends JPanel {

    private final List<Point2D.Double> polygon;
    private final List<Point2D.Double> pointsToTest;

    public PolygonVisualization(List<Point2D.Double> polygon, List<Point2D.Double> points) {
        this.polygon = polygon;
        this.pointsToTest = points;
        setPreferredSize(new Dimension(600, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // 绘制多边形
        for (int i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++) {
            Point2D.Double p1 = polygon.get(j);
            Point2D.Double p2 = polygon.get(i);
            g2d.drawLine((int) p1.x * 50, (int) p1.y * 50, (int) p2.x * 50, (int) p2.y * 50);
        }

        // 遍历所有点并绘制
        for (Point2D.Double pt : pointsToTest) {
            boolean isInside = PointInPolygon.isPointInPolygon(pt, polygon);
            boolean isOnBoundary = PointInPolygon.isPointOnPolygonBoundary(pt, polygon);

            if (!isInside && !isOnBoundary) {
                // 绘制外部的点为红色
                g2d.setColor(Color.RED);
                g2d.fillOval((int) pt.x * 50 - 5, (int) pt.y * 50 - 5, 10, 10);
            } else {
                // 绘制内部的点或边界上的点为绿色
                g2d.setColor(Color.GREEN);
                g2d.fillOval((int) pt.x * 50 - 5, (int) pt.y * 50 - 5, 10, 10);
            }
            g2d.setColor(Color.BLACK); // 将颜色还原为默认的黑色
        }
    }

    public static void main(String[] args) {
        // 示例输入：多边形顶点集合，按照顺时针或逆时针排序
        List<Point2D.Double> polygon = List.of(
                new Point2D.Double(1, 1),
                new Point2D.Double(1, 4),
                new Point2D.Double(3, 7),
                new Point2D.Double(5, 4),
                new Point2D.Double(5, 1)
        );

        // 示例输入：待测试的点集
        List<Point2D.Double> pointsToTest = List.of(
                new Point2D.Double(2, 2), // 在多边形内
                new Point2D.Double(1, 1), // 在多边形边上
                new Point2D.Double(3, 4), // 在多边形内
                new Point2D.Double(6, 6)  // 在多边形外
        );

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Polygon Visualization");
            PolygonVisualization visualization = new PolygonVisualization(polygon, pointsToTest);
            frame.add(visualization);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
