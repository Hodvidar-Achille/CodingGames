package com.hodvidar.utils.geometry;

public class Point3D extends Point {

    public final double z;

    public Point3D(final double x, final double y, final double z) {
        super(x, y);
        this.z = z;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Point3D p))
            return false;

        return this.x == p.x && this.y == p.y && this.z == p.z;
    }

    @Override
    public int hashCode() {
        return (int) (31 * this.x + 89 * this.y + this.z * 113);
    }

    @Override
    public String toString() {
        return "(" + this.x + "; " + this.y + "; " + this.z + ")";
    }

}
