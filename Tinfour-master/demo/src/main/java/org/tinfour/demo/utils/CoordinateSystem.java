package org.tinfour.demo.utils;

// 坐标系枚举，支持多种标准
public enum CoordinateSystem {
    WGS84("Global Coordinate System"),
    GCJ02("China Coordinate System"),
    XI_AN_20("XiAn 2000 Standard"),
    XI_AN_60("XiAn 1960 Standard");

    private final String description;

    CoordinateSystem(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
