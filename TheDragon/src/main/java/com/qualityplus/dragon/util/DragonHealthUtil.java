package com.qualityplus.dragon.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DragonHealthUtil {
    private static final double MAX_AMOUNT = 200D;
    private static final double MULTIPLIER = 1;

    public double getHealthAfterDamage(double max, double current, double damageCaused) {
        double basisHealthAfter = current - damageCaused;

        if (max < MAX_AMOUNT)
            return Math.max(basisHealthAfter, 0.0D);

        double heartRate = max / MAX_AMOUNT;

        double lifeWithoutDamage = (heartRate * MULTIPLIER) * current;

        double finalHealth = Math.max(lifeWithoutDamage, 0);

        return Math.min(Math.max((int)(finalHealth / heartRate), 0), MAX_AMOUNT);
    }

    public static double getNewHealth(double maxHealth, double health, double damage) {
        if (maxHealth < 200.0D)
            return Math.max(health - damage, 0.0D);
        double perOne = maxHealth / 200.0D;
        double newHealth = health * perOne - damage;
        return Math.min(getMinMax((int)(newHealth / perOne)), 200.0D);
    }
    public static int getMinMax(int division) {
        return Math.max(division, 0);
    }

    public double getHealth(double max, double current) {
        double heartRate = max / MAX_AMOUNT;

        return (heartRate * MULTIPLIER) * current;
    }
}
