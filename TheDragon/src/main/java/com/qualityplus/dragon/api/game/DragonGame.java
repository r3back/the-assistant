package com.qualityplus.dragon.api.game;

/**
 * The Dragon Game Implementation
 */
public interface DragonGame {
    /**
     * Start Dragon Game
     */
    void start();

    /**
     * Finish Dragon Game
     */
    void finish();

    /**
     *
     * @return If Game is in progress
     */
    boolean isActive();
}
