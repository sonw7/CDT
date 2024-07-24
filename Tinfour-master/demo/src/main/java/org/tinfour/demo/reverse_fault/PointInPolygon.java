package org.tinfour.demo.reverse_fault;

import java.awt.geom.Point2D;
import java.util.List;
//三、筛选点集 简单多边形
public class PointInPolygon {
        public static void main(String[] args) {
            //多边形顶点集合，按照顺时针或逆时针排序
            List<Point2D.Double> polygon = List.of(
                    new Point2D.Double(1, 1),
                    new Point2D.Double(1, 4),
                    new Point2D.Double(3, 7),
                    new Point2D.Double(5, 4),
                    new Point2D.Double(5, 1)
            );

            //待测试的点集
            List<Point2D.Double> points = List.of(
                    new Point2D.Double(2, 2), // 在多边形内
                    new Point2D.Double(1, 1), // 在多边形边上
                    new Point2D.Double(3, 4), // 在多边形内
                    new Point2D.Double(6, 6)  // 在多边形外
            );

            // 测试点集并输出结果
            for (Point2D.Double pt : points) {
                boolean isInside = isPointInPolygon(pt, polygon);//是否在边界内部
                boolean isOnBoundary = isPointOnPolygonBoundary(pt, polygon);//是否在边界上
                if (!isInside && !isOnBoundary) {
                    System.out.println("Point " + pt + " is outside the polygon.");
                }
            }
        }

        public static boolean isPointInPolygon(Point2D.Double point, List<Point2D.Double> polygon) {
            boolean result = false;
            int j = polygon.size() - 1;
            for (int i = 0; i < polygon.size(); i++) {
                if ((polygon.get(i).y > point.y) != (polygon.get(j).y > point.y) &&
                        (point.x < (polygon.get(j).x - polygon.get(i).x) * (point.y - polygon.get(i).y) / (polygon.get(j).y-polygon.get(i).y) + polygon.get(i).x)) {
                    result = !result;
                }
                j = i;
            }
            return result;
        }

        public static boolean isPointOnPolygonBoundary(Point2D.Double point, List<Point2D.Double> polygon) {
            final double EPSILON = 1e-10; // 用于处理浮点数计算的误差

            for (int i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++) {
                Point2D.Double p1 = polygon.get(j);
                Point2D.Double p2 = polygon.get(i);

                // 计算向量
                Point2D.Double vecEdge = new Point2D.Double(p2.x - p1.x, p2.y - p1.y);
                Point2D.Double vecPoint = new Point2D.Double(point.x - p1.x, point.y - p1.y);

                // 计算叉乘值
                double crossProduct = vecEdge.x * vecPoint.y - vecEdge.y * vecPoint.x;

                // 如果叉乘不为零，则点不在此边上
                if (Math.abs(crossProduct) > EPSILON) continue;

                // 点在线段p1-p2上的条件是：(point-p1) 和 (point-p2) 的内积应该为非正数
                double dotProduct = vecPoint.x * (point.x - p2.x) + vecPoint.y * (point.y - p2.y);
                if (dotProduct > EPSILON) continue;

                // 检查点是否在边的延长线上
                double squaredLengthEdge = vecEdge.x * vecEdge.x + vecEdge.y * vecEdge.y;
                double squaredLengthPoint = vecPoint.x * vecPoint.x + vecPoint.y * vecPoint.y;
                if (squaredLengthPoint > squaredLengthEdge) continue;

                // 到这一步，点就在边上
                return true;
            }
            return false;
        }


}
