package com.qualityplus.assistant.util.time;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public final class Markable extends OkaeriConfig {
    private long delay;
    private long lastMarked;

    /**
     * Easy Creation of a markable pojo
     *
     * @param delay      Timer.getEffectiveTime();
     * @param lastMarked System.currentTimeMillis();
     */
    public Markable(final long delay, final long lastMarked) {
        this.delay = delay;
        this.lastMarked = lastMarked;
    }

    public boolean isMarked() {
        return remainingTime() >= 0;
    }

    public void mark() {
        lastMarked = System.currentTimeMillis();
    }

    public long remainingTime() {
        return lastMarked + delay - System.currentTimeMillis();
    }

    public RemainingTime getRemainingTime() {
        final long millis = remainingTime();

        return TimeUtils.getRemainingTime(millis);
    }

}
