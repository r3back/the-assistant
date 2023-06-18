package com.qualityplus.assistant.util.random;

import com.qualityplus.assistant.api.util.Randomable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Randomable Item implementation
 *
 * @param <T> Generic item type
 */
@Getter
@Setter
@AllArgsConstructor
public final class EasyRandom<T> implements Randomable {
    private T item;
    private double probability;
}
