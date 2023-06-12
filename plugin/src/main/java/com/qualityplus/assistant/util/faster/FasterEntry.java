package com.qualityplus.assistant.util.faster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public final class FasterEntry<T, K> implements Map.Entry<T, K> {
    private T key;
    private K value;

    public static <T, K> FasterEntry<T, K> of(final T key, final K value) {
        return new FasterEntry<>(key, value);
    }

    @SafeVarargs
    public static <T, K> Set<FasterEntry<T, K>> setOf(final FasterEntry<T, K>... value) {
        return new HashSet<>(Arrays.asList(value));
    }

    @Override
    public K setValue(final K value) {
        this.value = value;
        return value;
    }
}