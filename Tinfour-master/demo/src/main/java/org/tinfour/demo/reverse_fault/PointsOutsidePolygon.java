package org.tinfour.demo.reverse_fault;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// 定义一个类用于存储三维点
class Point3D {
    double x, y, z;
    Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

// 主类，用于解决问题和可视化
public class PointsOutsidePolygon extends JPanel {

    private List<Point3D> polygon;
    private List<Point3D> points;
    private List<Point3D> outsidePoints;

    public PointsOutsidePolygon(List<Point3D> polygon, List<Point3D> points) {
        this.polygon = polygon;
        this.points = points;
        this.outsidePoints = getPointsOutsidePolygon(polygon, points);
    }

    public static void main(String[] args) {
        // 示例点集
        List<Point3D> A = new ArrayList<>();
        List<Point3D> B = new ArrayList<>();

        // 填充A和B点集
        // A 是形成多边形的点集
        // B 是需要检查的点集

        // 自相交的多边形（沙漏形状）
        A.add(new Point3D(100, 100, 0));
        A.add(new Point3D(200, 100, 0));
        A.add(new Point3D(150, 150, 0));
        A.add(new Point3D(200, 200, 0));
        A.add(new Point3D(100, 200, 0));
        A.add(new Point3D(150, 150, 0));

        // 点集B
        B.add(new Point3D(150, 150, 1));
        B.add(new Point3D(250, 250, 2));
        B.add(new Point3D(125, 175, 3));
        B.add(new Point3D(175, 125, 4));

        // 创建并设置窗口
        JFrame frame = new JFrame("Points Outside Polygon");
        PointsOutsidePolygon panel = new PointsOutsidePolygon(A, B);
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 绘制多边形
        int n = polygon.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            xPoints[i] = (int) polygon.get(i).x;
            yPoints[i] = (int) polygon.get(i).y;
        }

        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, n);

        // 绘制点集B
        for (Point3D point : points) {
            if (outsidePoints.contains(point)) {
                g2d.setColor(Color.RED); // 外部点
            } else {
                g2d.setColor(Color.GREEN); // 内部点
            }
            g2d.fillOval((int) point.x - 3, (int) point.y - 3, 6, 6);
        }
    }

    // 获取点集B中位于多边形A外的点
    public static List<Point3D> getPointsOutsidePolygon(List<Point3D> A, List<Point3D> B) {
        List<Point3D> outsidePoints = new ArrayList<>();

        for (Point3D point : B) {
            if (!isPointInsidePolygon(point, A)) {
                outsidePoints.add(point);
            }
        }

        return outsidePoints;
    }

    // 检查一个点是否在多边形内部
    public static boolean isPointInsidePolygon(Point3D point, List<Point3D> polygon) {
        int n = polygon.size();
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon.get(i).x, yi = polygon.get(i).y;
            double xj = polygon.get(j).x, yj = polygon.get(j).y;

            boolean intersect = ((yi > point.y) != (yj > point.y)) &&
                    (point.x < (xj - xi) * (point.y - yi) / (yj - yi) + xi);
            if (intersect) {
                inside = !inside;
            }
        }

        return inside;
    }
}
// 主类，用于解决问题和可视化
//public class PointsOutsidePolygon extends JPanel {
//
//    private List<Point3D> polygon;
//    private List<Point3D> points;
//    private List<Point3D> outsidePoints;
//
//    public PointsOutsidePolygon(List<Point3D> polygon, List<Point3D> points) {
//        this.polygon = polygon;
//        this.points = points;
//        this.outsidePoints = getPointsOutsidePolygon(polygon, points);
//    }
//
//    public static void main(String[] args) {
//        // 示例点集
//        List<Point3D> A = new ArrayList<>();
//        List<Point3D> B = new ArrayList<>();
//
//        // 填充A和B点集
//        // A 是形成多边形的点集
//        // B 是需要检查的点集
//
//        // 示例数据（替换为实际数据）
//        A.add(new Point3D(100, 100, 0));
//        A.add(new Point3D(200, 100, 0));
//        A.add(new Point3D(150, 150, 0));
//        A.add(new Point3D(200, 200, 0));
//        A.add(new Point3D(100, 200, 0));
//
//        B.add(new Point3D(150, 150, 1));
//        B.add(new Point3D(250, 250, 2));
//        B.add(new Point3D(125, 175, 3));
//
//        // 创建并设置窗口
//        JFrame frame = new JFrame("Points Outside Polygon");
//        PointsOutsidePolygon panel = new PointsOutsidePolygon(A, B);
//        frame.add(panel);
//        frame.setSize(400, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//
//        // 绘制多边形
//        int n = polygon.size();
//        int[] xPoints = new int[n];
//        int[] yPoints = new int[n];
//
//        for (int i = 0; i < n; i++) {
//            xPoints[i] = (int) polygon.get(i).x;
//            yPoints[i] = (int) polygon.get(i).y;
//        }
//
//        g2d.setColor(Color.BLACK);
//        g2d.drawPolygon(xPoints, yPoints, n);
//
//        // 绘制点集B
//        for (Point3D point : points) {
//            if (outsidePoints.contains(point)) {
//                g2d.setColor(Color.RED); // 外部点
//            } else {
//                g2d.setColor(Color.GREEN); // 内部点
//            }
//            g2d.fillOval((int) point.x - 3, (int) point.y - 3, 6, 6);
//        }
//    }
//
//    // 获取点集B中位于多边形A外的点
//    public static List<Point3D> getPointsOutsidePolygon(List<Point3D> A, List<Point3D> B) {
//        List<Point3D> outsidePoints = new ArrayList<>();
//
//        for (Point3D point : B) {
//            if (!isPointInsidePolygon(point, A)) {
//                outsidePoints.add(point);
//            }
//        }
//
//        return outsidePoints;
//    }
//
//    // 检查一个点是否在多边形内部
//    public static boolean isPointInsidePolygon(Point3D point, List<Point3D> polygon) {
//        int n = polygon.size();
//        boolean inside = false;
//
//        for (int i = 0, j = n - 1; i < n; j = i++) {
//            double xi = polygon.get(i).x, yi = polygon.get(i).y;
//            double xj = polygon.get(j).x, yj = polygon.get(j).y;
//
//            boolean intersect = ((yi > point.y) != (yj > point.y)) &&
//                    (point.x < (xj - xi) * (point.y - yi) / (yj - yi) + xi);
//            if (intersect) {
//                inside = !inside;
//            }
//        }
//
//        return inside;
//    }
//}

//import java.util.ArrayList;
//import java.util.List;
//
//// 定义一个类用于存储三维点
//class Point3D {
//    double x, y, z;
//    Point3D(double x, double y, double z) {
//        this.x = x;
//        this.y = y;
//        this.z = z;
//    }
//}
//
//// 主类，用于解决问题
//public class PointsOutsidePolygon {
//
//    public static void main(String[] args) {
//        // 示例点集
//        List<Point3D> A = new ArrayList<>();
//        List<Point3D> B = new ArrayList<>();
//
//        // 填充A和B点集
//        // A 是形成多边形的点集
//        // B 是需要检查的点集
//
//        // 示例数据（替换为实际数据）
//        A.add(new Point3D(0, 0, 0));
//        A.add(new Point3D(4, 0, 0));
//        A.add(new Point3D(4, 4, 0));
//        A.add(new Point3D(0, 4, 0));
//
//        B.add(new Point3D(2, 2, 1));
//        B.add(new Point3D(5, 5, 2));
//
//        // 获取在多边形外的点集B中的点
//        List<Point3D> outsidePoints = getPointsOutsidePolygon(A, B);
//
//        // 输出结果
//        for (Point3D p : outsidePoints) {
//            System.out.println("在多边形外的点: (" + p.x + ", " + p.y + ", " + p.z + ")");
//        }
//    }
//
//    // 获取点集B中位于多边形A外的点
//    public static List<Point3D> getPointsOutsidePolygon(List<Point3D> A, List<Point3D> B) {
//        List<Point3D> outsidePoints = new ArrayList<>();
//
//        for (Point3D point : B) {
//            if (!isPointInsidePolygon(point, A)) {
//                outsidePoints.add(point);
//            }
//        }
//
//        return outsidePoints;
//    }
//
//    // 检查一个点是否在多边形内部
//    public static boolean isPointInsidePolygon(Point3D point, List<Point3D> polygon) {
//        int n = polygon.size();
//        boolean inside = false;
//
//        for (int i = 0, j = n - 1; i < n; j = i++) {
//            double xi = polygon.get(i).x, yi = polygon.get(i).y;
//            double xj = polygon.get(j).x, yj = polygon.get(j).y;
//
//            boolean intersect = ((yi > point.y) != (yj > point.y)) &&
//                    (point.x < (xj - xi) * (point.y - yi) / (yj - yi) + xi);
//            if (intersect) {
//                inside = !inside;
//            }
//        }
//
//        return inside;
//    }
//}
//
