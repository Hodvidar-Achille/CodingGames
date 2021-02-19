package com.hodvidar.adventofcode.y2019;

public final class Moon {
    public double x;
    public double y;
    public double z;

    public double vX = 0;
    public double vY = 0;
    public double vZ = 0;

    public Moon(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Changes this Moon velocity <b>and also the given Moon velocity</b>
     * according to their position.
     *
     * @param other - other Moon which gravity change our velocity
     */
    public void adjustVelocities(Moon other) {
        if (this == other)
            throw new IllegalArgumentException("Other object cannot be self");

        this.vX += adjustVelocity(this.x, other.x);
        this.vY += adjustVelocity(this.y, other.y);
        this.vZ += adjustVelocity(this.z, other.z);

        other.vX += adjustVelocity(other.x, this.x);
        other.vY += adjustVelocity(other.y, this.y);
        other.vZ += adjustVelocity(other.z, this.z);
    }

    /**
     * Changes this Moon positions using its velocities values.
     */
    public void adjustPosition() {
        this.x += this.vX;
        this.y += this.vY;
        this.z += this.vZ;
    }

    private double adjustVelocity(double v1, double v2) {
        if (v1 < v2)
            return 1;
        if (v1 > v2)
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "(" + this.x + "; " + this.y + "; " + this.z + ") " + "V{"
                + this.vX + "; " + this.vY + "; " + this.vZ + "}";
    }

}
