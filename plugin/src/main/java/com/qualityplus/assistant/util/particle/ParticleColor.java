package com.qualityplus.assistant.util.particle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Color;

/**
 * Represent color of particles
 */
@Getter
@RequiredArgsConstructor
public enum ParticleColor {
    /**
     * Red
     */
    RED(Color.RED),
    /**
     * White
     */
    WHITE(Color.WHITE),
    /**
     * White
     */
    YELLOW(Color.YELLOW),
    /**
     * Blue
     */
    BLUE(Color.BLUE),
    /**
     * Cyan
     */
    CYAN(Color.CYAN),
    /**
     * Magenta
     */
    MAGENTA(Color.MAGENTA),
    /**
     * Green
     */
    GREEN(Color.GREEN),
    /**
     * Pink
     */
    PINK(Color.PINK),
    /**
     * Black
     */
    BLACK(Color.BLACK),
    /**
     * Dark Gray
     */
    DARK_GRAY(Color.DARK_GRAY),
    /**
     * Gray
     */
    GRAY(Color.GRAY),
    /**
     * Light Gray
     */
    LIGHT_GRAY(Color.LIGHT_GRAY);

    private final Color color;

}
