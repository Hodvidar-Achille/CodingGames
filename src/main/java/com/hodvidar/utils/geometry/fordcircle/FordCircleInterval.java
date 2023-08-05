package com.hodvidar.utils.geometry.fordcircle;

import com.hodvidar.utils.geometry.Circle;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * Interval between the Ford circles and the edge of the container.
 */
public class FordCircleInterval {

    @Nullable
    private FordCircle leftCircle;

    @Nullable
    private FordCircle rightCircle;

    private Double maxCircleRadiusPossible;

    public FordCircleInterval(@Nullable final FordCircle leftCircle,
                              @Nullable final FordCircle rightCircle) {
        this.leftCircle = leftCircle;
        this.rightCircle = rightCircle;
    }

    public Optional<FordCircle> getLeftCircle() {
        return Optional.ofNullable(leftCircle);
    }

    public void setLeftCircle(final @Nullable FordCircle leftCircle) {
        this.leftCircle = leftCircle;
    }

    public Optional<FordCircle> getRightCircle() {
        return Optional.ofNullable(rightCircle);
    }

    public void setRightCircle(final @Nullable FordCircle rightCircle) {
        this.rightCircle = rightCircle;
    }

    /**
     * Returns the maximum radius possible for a circle in this interval.
     *
     * @return the maximum radius possible for a circle in this interval.
     */
    public double maxCircleRadiusPossible() {
        if (maxCircleRadiusPossible != null) {
            return maxCircleRadiusPossible;
        }
        if (leftCircle == null && rightCircle == null) {
            maxCircleRadiusPossible = 0d;
            return maxCircleRadiusPossible;
        }
        if (leftCircle == null || rightCircle == null) {
            Circle onlyCircle = null;
            if (leftCircle == null) {
                onlyCircle = rightCircle;
            }
            if (rightCircle == null) {
                onlyCircle = leftCircle;
            }
            maxCircleRadiusPossible = maxCircleRadiusPossibleBetweenCircleAndEdge(onlyCircle);
            return maxCircleRadiusPossible;
        }
        maxCircleRadiusPossible = maxCircleRadiusPossibleBetweenTwoCircles(leftCircle, rightCircle);
        return maxCircleRadiusPossible;
    }

    private double maxCircleRadiusPossibleBetweenCircleAndEdge(final Circle onlyCircle) {
        if (onlyCircle == null) {
            return 0d;
        }
        final double onlyCircleRadius = onlyCircle.getRadius();
        final double distanceOfCircleCenterWithCorner = Math.sqrt(Math.pow(onlyCircleRadius, 2) + Math.pow(onlyCircleRadius, 2));
        final double distanceOfCircleEdgeWithCorner = distanceOfCircleCenterWithCorner - onlyCircleRadius;
        return Math.sqrt(distanceOfCircleEdgeWithCorner / 2);
    }

    private double maxCircleRadiusPossibleBetweenTwoCircles(final Circle leftCircle, final Circle rightCircle) {
        if (leftCircle == null || rightCircle == null) {
            return 0d;
        }
        /*
         Formula is: 1/sqrt(middleRadius) = 1/sqrt(leftRadius) + 1/sqrt(rightRadius)
         -->
         sqrt(middleRadius) = 1/(1/sqrt(leftRadius) + 1/sqrt(rightRadius))
         -->
            middleRadius = (1/(1/sqrt(leftRadius) + 1/sqrt(rightRadius))^2
         */
        final double leftRadius = leftCircle.getRadius();
        final double rightRadius = rightCircle.getRadius();
        return Math.pow(
                1 / (1 / Math.sqrt(leftRadius) + 1 / Math.sqrt(rightRadius)),
                2);
    }
}
