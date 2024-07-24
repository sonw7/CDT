package org.tinfour.demo.reverse_fault;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
//二、半圆内筛选点集
public class HalfCircleCheck {

    public static void main(String[] args) {
        Point2D.Double A = new Point2D.Double(0, 0);
        Point2D.Double B = new Point2D.Double(4, 0);
        Point2D.Double directionVector = new Point2D.Double(0, 1);
        // 方向向量 (dx, dy)待确定
        //上盘任取连续两点 做中垂线交下盘 判断是否在某两个连续下盘点之间。
        //在这四个点中，上下盘ge'qu

        List<Point2D.Double> points = new ArrayList<>();
        points.add(new Point2D.Double(2, 3));
        points.add(new Point2D.Double(3, -4));
        points.add(new Point2D.Double(5, 2));
        points.add(new Point2D.Double(1, 1));

        List<Point2D.Double> pointsInHalfCircle = getPointsInHalfCircle(A, B, directionVector, points);

        for (Point2D.Double point : pointsInHalfCircle) {
            System.out.println("Point in half circle: " + point);
        }
    }

    public static List<Point2D.Double> getPointsInHalfCircle(Point2D.Double A, Point2D.Double B,Point2D.Double directionVector, List<Point2D.Double> points) {

        List<Point2D.Double> pointsInHalfCircle = new ArrayList<>();

        // 计算线段 AB 的中点
        Point2D.Double center = new Point2D.Double((A.x + B.x) / 2, (A.y + B.y) / 2);
        // 计算半径
        double radius = center.distance(A);
        // 计算向量 CA
        Point2D.Double CA = new Point2D.Double(A.x - center.x, A.y - center.y);
        // 计算向量 CB
        Point2D.Double CB = new Point2D.Double(B.x - center.x, B.y - center.y);
                // 计算向量 CA 和方向向量 d 的叉积
        double crossProductCA_D = CA.x * directionVector.y - CA.y * directionVector.x;
        //double crossProductCB_D = CB.x * directionVector.y - CB.y * directionVecto

        for (Point2D.Double point : points) {
            // 计算点 P 到圆心的距离
            double distanceToCenter = center.distance(point);
                    // 判断点是否在圆内
            if (distanceToCenter <= radius) {
                // 计算向量 CP
                Point2D.Double CP = new Point2D.Double(point.x - center.x, point.y - center.y);

                // 计算向量 CP 和 CA 的叉积
                double crossProductCP_CA = CP.x * CA.y - CP.y * CA.x;
                // 计算向量 CP 和 CB 的叉积
                double crossProductCP_CB = CP.x * CB.y - CP.y * CB.x;
                // 计算向量 CP 和方向向量 d 的叉积
                double crossProductCP_D = CP.x * directionVector.y - CP.y * directionVector.x;

                // 判断点是否在 CA 和方向向量 d 之间
                if (crossProductCA_D > 0) {
                    // CA 在方向向量 d 的顺时针方向,那么CB一定在d的逆时针方向
                    if (crossProductCP_CA < 0 && crossProductCP_D > 0) {
                        //如果CP在CA的逆时针方向且在方向向量D的顺时针方向，则符合要求
                        pointsInHalfCircle.add(point);
                    }else if(crossProductCP_CB > 0 && crossProductCP_D < 0){
                        //点不在CA向量和方向向量D之间，此时如果在CB顺时针方向且在方向向量D的逆时针方向也满足要求
                        pointsInHalfCircle.add(point);
                    }
                } else {
                    // CA 在方向向量 d 的逆时针方向，那么CB一定在d的顺时针方向
                    if (crossProductCP_CA > 0 && crossProductCP_D < 0) {
                        pointsInHalfCircle.add(point);
                    }else if(crossProductCP_CB > 0 && crossProductCP_D < 0){
                        // 判断点是否在 CB 和方向向量 d 之间
                        //点不在CA向量和方向向量D之间，此时如果在CB逆时针方向且在方向向量D的顺时针方向也满足要求
                        pointsInHalfCircle.add(point);
                    }
                }
            }
        }
        return pointsInHalfCircle;
    }
}
