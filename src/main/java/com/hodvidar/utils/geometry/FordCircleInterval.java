package com.hodvidar.utils.geometry;

import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * Interval between the Ford circles and the edge of the container.
 */
public class FordCircleInterval {

    private Optional<Circle> leftCircle;

    private Optional<Circle> rightCircle;

    public FordCircleInterval(final Optional<Circle> leftCircle, final Optional<Circle> rightCircle) {
        this.leftCircle = leftCircle;
        this.rightCircle = rightCircle;
    }

    public Optional<Circle> getLeftCircle() {
        return leftCircle;
    }

    public void setLeftCircle(final @Nullable Circle leftCircle) {
        this.leftCircle = Optional.ofNullable(leftCircle);
    }

    public Optional<Circle> getRightCircle() {
        return rightCircle;
    }

    public void setRightCircle(final @Nullable Circle rightCircle) {
        this.rightCircle = Optional.ofNullable(rightCircle);
    }

    /**
     * Returns the maximum radius possible for a circle in this interval.
     * @return the maximum radius possible for a circle in this interval.
     */
    public double maxCircleRadiusPossible() {
        if(leftCircle.isEmpty() && rightCircle.isEmpty()){
            return 0d;
        }
        if(leftCircle.isEmpty() || rightCircle.isEmpty()){
            Circle onlyCircle = null;
            if(leftCircle.isEmpty()){
                onlyCircle = rightCircle.get();
            }
            if (rightCircle.isEmpty()){
                onlyCircle = leftCircle.get();
            }
            return maxCircleRadiusPossibleBetweenCircleAndEdge(onlyCircle);
        }

        return 0d;
    }
    private double maxCircleRadiusPossibleBetweenCircleAndEdge(final Circle onlyCircle) {
        if(onlyCircle == null){
            return 0d;
        }
        final double onlyCircleRadius = onlyCircle.getRadius();
        final double distanceOfCircleCenterWithCorner = Math.sqrt(Math.pow(onlyCircleRadius, 2) + Math.pow(onlyCircleRadius, 2));
        final double distanceOfCircleEdgeWithCorner = distanceOfCircleCenterWithCorner - onlyCircleRadius;
        return Math.sqrt(distanceOfCircleEdgeWithCorner/2);
    }

    private double maxCircleRadiusPossibleBetweenTwoCircles(final Circle leftCircle, final Circle rightCircle) {
        if(leftCircle == null || rightCircle == null){
            return 0d;
        }
        final double leftCircleRadius = leftCircle.getRadius();
        final double rightCircleRadius = rightCircle.getRadius();
        // TODO
        return 0d;
    }
}
