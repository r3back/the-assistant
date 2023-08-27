package com.qualityplus.assistant.util.actionbar;

import lombok.experimental.UtilityClass;

/**
 * Utility class for Skills ActionBar
 */
@UtilityClass
public class ActionBarUtils {
    private static final int ACTION_BAR_LENGTH = 20;

    /**
     * Retrieves what percentage from 0-100% is the amount
     * from total
     *
     * @param amount initial amount
     * @param total  total amount
     * @return percentage
     */
    public static double getPercentageFromTotal(final Double amount, final Double total) {
        return amount > 0 ? (amount * 100) / total : 0D;
    }

    /**
     * Retrieves what percentage from 0-100% is the amount
     * from total which is 20%
     *
     * @param percentage percentage
     * @return percentage
     */
    public int getParsedInPercentage(final int percentage) {
        return percentage > 0 ? (percentage * ACTION_BAR_LENGTH) / 100 : 0;
    }

    /**
     * Return action bar for specific int percentage
     *
     * @param percentage percentage amount
     * @return action bar
     */
    public String getReplacedBar(final int percentage) {
        final StringBuilder bar = new StringBuilder();

        final int current = getParsedInPercentage(percentage);

        for (int i = 0; i < current && i < ACTION_BAR_LENGTH; i++) {
            bar.append("&a-");
        }

        for (int i = 0; i < ACTION_BAR_LENGTH - current; i++) {
            bar.append("&f-");
        }

        return bar.toString();
    }

    /**
     * Return action bar for specific double percentage
     *
     * @param percentage percentage amount
     * @return action bar
     */
    public String getReplacedBar(final double percentage) {
        return getReplacedBar((int) percentage);
    }
}
