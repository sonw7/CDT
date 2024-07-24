package org.tinfour.demo.reverse_fault;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//一、方向向量取值
public class Vector_computation {

    public static void main(String[] args) {
        List<Point2D> pointSet1 = new ArrayList<>();
        List<Point2D> pointSet2 = new ArrayList<>();

        // Populate the point sets with example points
        // Example points for pointSet1
        pointSet1.add(new Point2D.Double(1, 1));
        pointSet1.add(new Point2D.Double(3, 4));
        pointSet1.add(new Point2D.Double(6, 1));

        // Example points for pointSet2
        pointSet2.add(new Point2D.Double(2, 2));
        pointSet2.add(new Point2D.Double(5, 3));
        pointSet2.add(new Point2D.Double(7, 5));

        // Choose random consecutive points A and B from pointSet1
        Random rand = new Random();
        int index = rand.nextInt(pointSet1.size() - 1);
        Point2D A = pointSet1.get(index);
        Point2D B = pointSet1.get(index + 1);

        System.out.println("Chosen points A: " + A + " and B: " + B);

        // Calculate the perpendicular bisector of AB
        Line2D bisector = calculatePerpendicularBisector(A, B);

        // Check for intersection with line segments in pointSet2
        for (int i = 0; i < pointSet2.size() - 1; i++) {
            Point2D C = pointSet2.get(i);
            Point2D D = pointSet2.get(i + 1);
            Line2D segment = new Line2D.Double(C, D);

            if (bisector.intersectsLine(segment)) {
                System.out.println("Intersection found with segment: " + C + " - " + D);
                // Return one of the vectors (BC, BD, AC, AD)
                System.out.println("Vector BC: " + vectorToString(B, C));
                System.out.println("Vector BD: " + vectorToString(B, D));
                System.out.println("Vector AC: " + vectorToString(A, C));
                System.out.println("Vector AD: " + vectorToString(A, D));
                return; // Exit after finding the first intersection
            }
        }

        System.out.println("No intersection found.");
    }

    private static Line2D calculatePerpendicularBisector(Point2D A, Point2D B) {
        // Midpoint of AB
        double midX = (A.getX() + B.getX()) / 2;
        double midY = (A.getY() + B.getY()) / 2;

        // Slope of AB
        double dx = B.getX() - A.getX();
        double dy = B.getY() - A.getY();

        // Perpendicular slope
        double perpendicularSlope = -dx / dy;

        // Endpoints of the bisector line (arbitrarily chosen length of 10 units in each direction)
        double length = 10;
        double endX1 = midX + length / Math.sqrt(1 + perpendicularSlope * perpendicularSlope);
        double endY1 = midY + perpendicularSlope * (endX1 - midX);
        double endX2 = midX - length / Math.sqrt(1 + perpendicularSlope * perpendicularSlope);
        double endY2 = midY + perpendicularSlope * (endX2 - midX);

        return new Line2D.Double(endX1, endY1, endX2, endY2);
    }

    private static String vectorToString(Point2D from, Point2D to) {
        return "[" + (to.getX() - from.getX()) + ", " + (to.getY() - from.getY()) + "]";
    }
}
