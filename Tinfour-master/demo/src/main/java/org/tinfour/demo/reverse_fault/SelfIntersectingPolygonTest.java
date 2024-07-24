package org.tinfour.demo.reverse_fault;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import java.util.ArrayList;
import java.util.List;
//自相交多边形
public class SelfIntersectingPolygonTest {

    private static GeometryFactory geometryFactory = new GeometryFactory();

    public static void main(String[] args) {
        // 自相交多边形的顶点集合
        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(0, 0),
                new Coordinate(2, 2),
                new Coordinate(0, 2),
                new Coordinate(2, 0),
                new Coordinate(0, 0)
        };

        // 创建一个可能自相交的多边形
        Polygon selfIntersectingPolygon = createPolygon(coordinates);

        // 离散点集合B
        Coordinate[] testPoints = new Coordinate[] {
                new Coordinate(1, 1), // 在自相交区域内
                new Coordinate(3, 1), // 在自相交区域外
                new Coordinate(0.5, 1.5), // 在另一个三角形区域内
                new Coordinate(-1, -1)   // 明显在多边形外部
        };

        for (Coordinate testPoint : testPoints) {
            Point point = geometryFactory.createPoint(testPoint);
            boolean isInside = selfIntersectingPolygon.contains(point);
            System.out.println("Point " + testPoint + " is inside the self-intersecting polygon: " + isInside);
        }
    }

    private static Polygon createPolygon(Coordinate[] coordinates) {
        if (coordinates == null || coordinates.length < 4) {
            throw new IllegalArgumentException("A polygon must have at least 4 coordinates including the closing endpoint.");
        }

        LinearRing shell = new LinearRing(new CoordinateArraySequence(coordinates), geometryFactory);
        return new Polygon(shell, null, geometryFactory);
    }
}

