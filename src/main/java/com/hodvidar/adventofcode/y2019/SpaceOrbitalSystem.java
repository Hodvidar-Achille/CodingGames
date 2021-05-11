package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.number.ArithmeticServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Space orbital sytem that is composed of a number of objects (Moon)
 * that modify each other velocities.
 *
 * @author Hodvidar
 */
public final class SpaceOrbitalSystem {
    private final List<Moon> initialState = new ArrayList<>();
    private final List<Moon> moons = new ArrayList<>();

    private Double energy = null;
    private boolean changedStep = false;

    public SpaceOrbitalSystem(final Moon... moons) {
        for (final Moon m : moons) {
            this.moons.add(m);
            this.initialState.add(new Moon(m.x, m.y, m.z));
        }
    }

    // -------------------------------------------------------------------
    public void doSteps(final int numberOfStep) {
        for (int i = 1; i <= numberOfStep; i++) {
            this.doOneStep();
        }
    }

    private void doOneStep() {
        changedStep = true;
        for (int i = 0; i < this.moons.size(); i++) {
            final Moon m1 = this.moons.get(i);
            for (int j = i + 1; j < this.moons.size(); j++) {
                final Moon m2 = this.moons.get(j);
                m1.adjustVelocities(m2);
            }
        }

        for (final Moon m : this.moons)
            m.adjustPosition();
    }

    /**
     * Returns the total energy in the system (= sum of total energy of all moons). <br/>
     * The total energy for a single moon is
     * its potential energy multiplied by its kinetic energy. <br/>
     * A moon's potential energy is the sum of the absolute values
     * of its x, y, and z position coordinates. <br/>
     * A moon's kinetic energy is the sum of the absolute values
     * of its velocity coordinates. <br/>
     *
     * @return energy of the system.
     */
    public double getTotalEnergy() {
        if (!changedStep && this.energy != null)
            return this.energy;

        this.energy = this.calculateSystemEnergy();
        changedStep = false;
        return this.energy;
    }

    private double calculateSystemEnergy() {
        double total = 0;
        for (final Moon m : this.moons) {
            final double pot = Math.abs(m.x) + Math.abs(m.y) + Math.abs(m.z);
            final double kin = Math.abs(m.vX) + Math.abs(m.vY) + Math.abs(m.vZ);
            total += pot * kin;
        }
        return total;
    }

    // -------------------------------------------------------------------

    /**
     * Look how many steps are required for the system to go back to its initial state. <br/>
     * (Same coordinates and same velocity for each Moon).
     * <p>
     * <b>NOT OPTIMIZED, (really long)</b>
     * </p>
     *
     * @return
     */
    @Deprecated
    public double getNumberOfStepForLoop() {
        double nb = 0;
        while (true) {
            doOneStep();
            nb++;
            if (isBackToInitState())
                break;
        }
        return nb;
    }

    private boolean isBackToInitState() {
        for (int i = 0; i < this.moons.size(); i++) {
            if (areNotSame(this.initialState.get(i), this.moons.get(i)))
                return false;
        }
        return true;
    }

    private boolean areNotSame(final Moon m1, final Moon m2) {
        return m1.x != m2.x || m1.y != m2.y || m1.z != m2.z || m1.vX != m2.vX
                || m1.vY != m2.vY || m1.vZ != m2.vZ;
    }
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    public double getNumberOfStepForLoop_optimized() {
        double nbX = 0;
        double nbY = 0;
        double nbZ = 0;
        boolean xFound = false;
        boolean yFound = false;
        boolean zFound = false;
        while (true) {
            if (xFound && yFound && zFound)
                break;

            if (!xFound)
                nbX++;
            if (!yFound)
                nbY++;
            if (!zFound)
                nbZ++;

            doOneStep();

            if (!xFound && isBackToInitStateForX())
                xFound = true;
            if (!yFound && isBackToInitStateForY())
                yFound = true;
            if (!zFound && isBackToInitStateForZ())
                zFound = true;
        }

        // Find LCD
        final double lcd = getLCM(nbX, nbY, nbZ);
        return lcd;
    }

    private double getLCM(final double a, final double b, final double c) {
        return ArithmeticServices.lowerCommonMultiplier(a, b, c);
    }

    private boolean isBackToInitStateForX() {
        for (int i = 0; i < this.moons.size(); i++) {
            if (areNotSameX(this.initialState.get(i), this.moons.get(i)))
                return false;
        }
        return true;
    }

    private boolean areNotSameX(final Moon m1, final Moon m2) {
        return m1.x != m2.x || m1.vX != m2.vX;
    }

    private boolean isBackToInitStateForY() {
        for (int i = 0; i < this.moons.size(); i++) {
            if (areNotSameY(this.initialState.get(i), this.moons.get(i)))
                return false;
        }
        return true;
    }

    private boolean areNotSameY(final Moon m1, final Moon m2) {
        return m1.y != m2.y || m1.vY != m2.vY;
    }

    private boolean isBackToInitStateForZ() {
        for (int i = 0; i < this.moons.size(); i++) {
            if (areNotSameZ(this.initialState.get(i), this.moons.get(i)))
                return false;
        }
        return true;
    }

    private boolean areNotSameZ(final Moon m1, final Moon m2) {
        return m1.z != m2.z || m1.vZ != m2.vZ;
    }
    // -------------------------------------------------------------------

    /**
     * Returns a copy of the list of this system's Moons
     **/
    public List<Moon> getMoons() {
        final List<Moon> copy = new ArrayList<>();
        Collections.copy(copy, this.moons);
        return copy;
    }

    @Override
    public String toString() {
        String s = "------------------------------\n";
        for (final Moon m : this.moons) {
            s += m.toString() + "\n";
        }
        s += "------------------------------";
        return s;
    }
}
