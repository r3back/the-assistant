package com.qualityplus.assistant.util.random;

import com.qualityplus.assistant.api.util.Randomable;

import java.util.List;
import java.util.Random;

/**
 * Handles selection between Randomable items
 *
 * @param <T> Generic Randomable item
 */
public final class RandomSelector<T extends Randomable> {
    private final Random random = new Random();
    private final List<T> items;
    private double totalSum = 0;

    /**
     * Constructor with default items
     *
     * @param items List of Randomable Items
     */
    public RandomSelector(final List<T> items) {
        this.items = items;

        for (final T item : this.items) {
            this.totalSum = this.totalSum + item.getProbability();
        }
    }

    /**
     * Retrieves a random item or null if list is empty
     * or items aren't more than 1
     *
     * @return Generic Random item
     */
    public T getRandom() {
        if (this.items == null || this.items.size() == 1) {
            return null;
        }

        return getCommonRandom();
    }

    /**
     * Retrieves a random item from list, if list items
     * is empty retrieves null and if list size is equals to 1
     * retrieves the unique item in list
     *
     * @return Generic Random item
     */
    public T getRandomOrUniqueItem() {
        if (this.items == null) {
            return null;
        }

        if (this.items.size() == 1) {
            return this.items.get(0);
        }

        return getCommonRandom();
    }

    private T getCommonRandom() {
        final int index = this.random.nextInt((int) this.totalSum);

        double sum = 0;

        int i = 0;

        while (sum < index) {
            sum = sum + this.items.get(i++).getProbability();
        }

        return this.items.get(Math.max(0, i - 1));
    }
}
