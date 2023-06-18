package com.qualityplus.assistant.util.time;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.Arrays;

/**
 * Human time representation
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class HumanTime extends OkaeriConfig {
    private int amount;
    private TimeType type;

    /**
     * Retrieves time as milliseconds
     *
     * @return time as milliseconds
     */
    public long getEffectiveTime() {
        return getDuration().toMillis();
    }

    /**
     * Retrieves time as seconds
     *
     * @return time as seconds
     */
    public long getSeconds() {
        return getDuration().getSeconds();
    }

    private Duration getDuration() {
        switch (type) {
            case MINUTES:
                return Duration.ofMinutes(amount);
            case DAYS:
                return Duration.ofDays(amount);
            case HOURS:
                return Duration.ofHours(amount);
            default:
                return Duration.ofSeconds(amount);
        }
    }

    /**
     * Represent different human time
     */
    @Getter
    @RequiredArgsConstructor
    public enum TimeType{
        /**
         * Minutes
         */
        MINUTES(3),
        /**
         * Hours
         */
        HOURS(2),
        /**
         * Seconds
         */
        SECONDS(1),
        /**
         * Days
         */
        DAYS(0);

        private final int level;

        /**
         * Retrieves next time type
         *
         * @return next time type
         */
        public TimeType getNext() {
            return Arrays.stream(TimeType.values())
                    .filter(timeType -> timeType.getLevel() == this.level + 1)
                    .findFirst()
                    .orElse(TimeType.DAYS);
        }
    }
}