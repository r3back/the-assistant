package com.qualityplus.assistant.util.time;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Markable cooldown
 */
@Getter
@Setter
@NoArgsConstructor
public final class Markable extends OkaeriConfig {
    private long delay;
    private long lastMarked;

    /**
     * Easy Creation of a cooldown
     *
     * @param delay      Timer.getEffectiveTime();
     * @param lastMarked System.currentTimeMillis();
     */
    public Markable(final long delay, final long lastMarked) {
        this.lastMarked = lastMarked;
        this.delay = delay;
    }

    /**
     *
     * @return true if still on cooldown
     */
    public boolean isMarked() {
        return remainingTime() >= 0;
    }

    /**
     * Mark cooldown to current system millis
     */
    public void mark() {
        this.lastMarked = System.currentTimeMillis();
    }

    /**
     * Retrieves Remaining time expressed with milliseconds
     *
     * @return milliseconds remaining time
     */
    public long remainingTime() {
        return this.lastMarked + this.delay - System.currentTimeMillis();
    }

    /**
     * Retrieves Remaining time
     *
     * @return {@link RemainingTime}
     */
    public RemainingTime getRemainingTime() {
        final long millis = remainingTime();

        return TimeUtils.getRemainingTime(millis);
    }

}
