package com.qualityplus.assistant.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;

/**
 * Utility Math Class
 */
@UtilityClass
@SuppressWarnings("all")
public final class MathUtil {
    private final DecimalFormat ROUND_COMMA_FORMAT = new DecimalFormat("#,###");
    private final DecimalFormat ROUND_DOT_FORMAT = new DecimalFormat("#.##");
    private final double[] SIN_FORMAT = new double[65536];

    /**
     * Retrieves percentage of a number over a total
     *
     * @param number     number
     * @param percentage percentage
     * @return
     */
    public double getPercentage(final double number, final double percentage) {
        return (percentage * number) / 100;
    }

    /**
     * Calculates the sin() of a double value
     *
     * @param a number
     * @return sin() of number
     */
    public double sinCalc(final double a) {
        final float f = (float) a;
        return SIN_FORMAT[(int) (f * 10430.378F) & '\uffff'];
    }

    /**
     * Sum Numbers
     *
     * @param a first value
     * @param b second value
     * @return sum of a and b
     * @param <T> Generic number
     */
    public <T extends Number> T sumNumbers(final Number a, final Number b) {
        if (a instanceof Double) {
            return (T) new Double(a.doubleValue() + b.doubleValue());
        } else {
            return (T) new Integer(a.intValue() + b.intValue());
        }
    }

    /**
     * Subtract Numbers
     *
     * @param a first value
     * @param b second value
     * @return subtract of a and b
     * @param <T> Generic number
     */
    public <T extends Number> T subtractNumbers(final Number a, final Number b) {
        if (a instanceof Double) {
            return (T) new Double(a.doubleValue() - b.doubleValue());
        } else {
            return (T) new Integer(a.intValue() - b.intValue());
        }
    }


    /**
     * Retrieves a double number round as string
     * @param value double value
     * @return round number
     */
    public String round(final double value) {
        if (value <= 999) {
            return ROUND_DOT_FORMAT.format(value);
        } else {
            return ROUND_COMMA_FORMAT.format(value);
        }
    }


    /**
     * Retrieves offset between two locations
     *
     * @param paramLocation1 {@link Location}
     * @param paramLocation2 {@link Location}
     * @return offset value
     */
    public double offset(final Location paramLocation1, final Location paramLocation2) {
        return offset(paramLocation1.toVector(), paramLocation2.toVector());
    }

    /**
     * Retrieves offset between two vectors
     *
     * @param paramVector1 {@link Vector}
     * @param paramVector2 {@link Vector}
     * @return offset value
     */
    public double offset(final Vector paramVector1, final Vector paramVector2) {
        return paramVector1.subtract(paramVector2).length();
    }
}
