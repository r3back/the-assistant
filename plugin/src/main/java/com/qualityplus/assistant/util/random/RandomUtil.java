package com.qualityplus.assistant.util.random;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Utility class for random number generation
 */
@UtilityClass
public final class RandomUtil {
    /**
     * Retrieves a random integer between two values
     * @param min min random value
     * @param max max random value
     * @return random number
     */
    public int randomIntBetween(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Retrieves a random number given a max value
     *
     * @param max max random value
     * @return random number
     */
    public int randomUpTo(final int max) {
        return randomIntBetween(0, max);
    }

    /**
     * Retrieves a random double between two values
     * @param min min random value
     * @param max max random value
     * @return random number
     */
    public double randomBetween(final double min, final double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    /**
     * Retrieves a random item given a map with item-chance
     *
     * @param probabilitiesMap Map with Item as key and value as chance
     * @return Generic Random item
     * @param <T> Generic Item value
     */
    @Nullable
    public static <T> T getRandom(final Map<T, Double> probabilitiesMap) {
        final List<EasyRandom<T>> items = probabilitiesMap.entrySet()
                .stream()
                .map(RandomUtil::itemEntryToRandomItem)
                .collect(Collectors.toList());

        return getRandomElement(items);
    }

    /**
     * Retrieves a random item given a list of items
     *
     * @param items List of generic items
     * @return Generic Random item
     * @param <T> Generic Item value
     */
    @Nullable
    public static <T> T getRandom(final List<T> items) {
        final List<EasyRandom<T>> randomItems = items
                .stream()
                .map(RandomUtil::itemToRandomItem)
                .collect(Collectors.toList());

        return getRandomElement(randomItems);
    }

    private static <T> T getRandomElement(final List<EasyRandom<T>> items) {
        final RandomSelector<EasyRandom<T>> selector = new RandomSelector<>(items);

        return Optional.ofNullable(selector.getRandom())
                .map(EasyRandom::getItem)
                .orElse(null);
    }

    private static <T> EasyRandom<T> itemEntryToRandomItem(final Map.Entry<T, Double> entry) {
        return new EasyRandom<>(entry.getKey(), entry.getValue());
    }

    private static <T> EasyRandom<T> itemToRandomItem(final T item) {
        return new EasyRandom<>(item, RandomUtil.randomBetween(0, 100));
    }
}
