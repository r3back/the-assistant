package com.qualityplus.assistant.util.time;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for time
 *
 * TODO Clarify methods getWhenAgo and getRemainingTime
 */
@UtilityClass
public final class TimeUtils {
    /**
     * Retrieves message with parsed time
     *
     * @param message      mesage
     * @param days         days format
     * @param hours        hours format
     * @param minutes      minutes format
     * @param seconds      seconds format
     * @param time         {@link RemainingTime}
     * @param noTimeSymbol symbol when time is less than zero
     * @param show         if symbol should be show when time is less than zero
     * @return message with parsed time
     */
    public String getParsedTime(final RemainingTime time, final String message, final String days,
                                final String hours, final String minutes, final String seconds,
                                final String noTimeSymbol, final boolean show) {

        final String symbolWhenIsEmpty = show ? noTimeSymbol : "";

        final List<IPlaceholder> placeholders = Arrays.asList(
                getTimePlaceholder(time.getDays(), "days", days, symbolWhenIsEmpty),
                getTimePlaceholder(time.getHours(), "hours", hours, symbolWhenIsEmpty),
                getTimePlaceholder(time.getMinutes(), "minutes", minutes, symbolWhenIsEmpty),
                getTimePlaceholder(time.getSeconds(), "seconds", seconds, symbolWhenIsEmpty)
        );

        return StringUtils.processMulti(message, placeholders);
    }

    /**
     * Retrieves how many long ago happened given a pastime
     *
     * @param pastTime pasttime
     * @return {@link RemainingTime}
     */
    public RemainingTime getTimeWhenAgo(final long pastTime) {
        final long millis = System.currentTimeMillis() - pastTime;

        return getRemainingTime(millis);
    }

    /**
     * Retrieves how many long ago happened given a pastime
     *
     * @param millisParam pasttime
     * @return {@link RemainingTime}
     */
    public RemainingTime getRemainingTime(final long millisParam) {
        long millis = millisParam;

        final long nanos = TimeUnit.MILLISECONDS.toNanos(millis);
        final long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        final long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return new RemainingTime(days, hours, minutes, seconds, millis, nanos);
    }


    private Placeholder getTimePlaceholder(final long time, final String placeholder, final String placeholderValue,
                                           final String symbolWhenIsEmpty) {
        final String timeStr = String.valueOf(time);

        final String placeholderKey = placeholder + "_placeholder";

        final String finalValue = time >= 0 ? new Placeholder(placeholder, timeStr).process(placeholderValue) : symbolWhenIsEmpty;

        return new Placeholder(placeholderKey, finalValue);
    }
}
