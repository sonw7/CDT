package org.tinfour.demo.reverse_fault;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PointInPolygon {

    // 检查点是否在多边形内
    public static boolean isPointInPolygon(Point2D point, List<Point2D> polygonPoints) {
        Path2D polygon = new Path2D.Double();
        if (polygonPoints.size() > 0) {
            Point2D firstPoint = polygonPoints.get(0);
            polygon.moveTo(firstPoint.getX(), firstPoint.getY());
            for (int i = 1; i < polygonPoints.size(); i++) {
                Point2D nextPoint = polygonPoints.get(i);
                polygon.lineTo(nextPoint.getX(), nextPoint.getY());
            }
            polygon.closePath(); // Closes the path back to the first point
        }
        return polygon.contains(point.getX(), point.getY());
    }

    // 获取闭环内的所有点集 D 中的点
    public static List<Point2D> getPointsInsideBoundary(List<Point2D> boundaryPoints, List<Point2D> pointsToCheck) {
        List<Point2D> pointsInside = new ArrayList<>();
        for (Point2D point : pointsToCheck) {
            if (isPointInPolygon(point, boundaryPoints)) {
                pointsInside.add(point);
            }
        }
        return pointsInside;
    }

    public static void main(String[] args) {
        // 构造闭环边界：点 A、B 和点集 C 的连线构成
        List<Point2D> boundaryPoints = new ArrayList<>();
        boundaryPoints.add(new Point2D.Double(1, 1)); // 点 A
        boundaryPoints.add(new Point2D.Double(5, 1)); // 点 B
        // 添加点集 C 的顺序点
        boundaryPoints.add(new Point2D.Double(4, 3));
        boundaryPoints.add(new Point2D.Double(6, 5));
        boundaryPoints.add(new Point2D.Double(3, 6));
        boundaryPoints.add(new Point2D.Double(1, 4));

        // 已知的点集 D，包括在多边形外部和边缘上的点
        List<Point2D> pointsToCheck = new ArrayList<>();
        pointsToCheck.add(new Point2D.Double(3, 2)); // 在多边形内
        pointsToCheck.add(new Point2D.Double(2, 2)); // 在多边形内
        pointsToCheck.add(new Point2D.Double(5, 5)); // 在多边形外
        pointsToCheck.add(new Point2D.Double(3, 4)); // 在多边形内
        pointsToCheck.add(new Point2D.Double(7, 3)); // 在多边形外
        pointsToCheck.add(new Point2D.Double(3, 1)); // 在多边形边缘
        pointsToCheck.add(new Point2D.Double(5, 3)); // 在多边形外

        // 从点集 D 中筛选位于闭环边界内的点
        List<Point2D> pointsInsideBoundary = getPointsInsideBoundary(boundaryPoints, pointsToCheck);

        // 输出结果
        for (Point2D point : pointsInsideBoundary) {
            System.out.println("Point inside: (" + point.getX() + ", " + point.getY() + ")");
        }
    }
}
