package com.qualityplus.assistant.util.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Build to bring together multiple lists
 *
 * @param <T> Generic List builder
 */
public final class ListBuilder<T> {
    private final List<T> list = new ArrayList<>();

    /**
     * Adds list values to list
     *
     * @param list List of generic type
     * @return {@link ListBuilder}
     */
    public ListBuilder<T> with(final List<T> list) {
        this.list.addAll(list);
        return this;
    }

    /**
     * Adds list builder values to list
     *
     * @param builder {@link ListBuilder}
     * @return {@link ListBuilder}
     */
    public ListBuilder<T> with(final ListBuilder<T> builder) {
        this.list.addAll(builder.get());
        return this;
    }

    /**
     * Add array values to list
     *
     * @param data Array of generic type
     * @return {@link ListBuilder}
     */
    public ListBuilder<T> with(final T... data) {
        this.list.addAll(Arrays.stream(data)
                .collect(Collectors.toList()));
        return this;
    }

    /**
     * Retrieve list with all values
     *
     * @return List of generic type
     */
    public List<T> get() {
        return this.list;
    }
}
