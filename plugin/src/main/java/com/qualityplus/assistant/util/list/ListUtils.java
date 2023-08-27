package com.qualityplus.assistant.util.list;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Lists
 */
@UtilityClass
public final class ListUtils {
    /**
     * Retrieves a list with an added item
     *
     * @param list  List of generic type
     * @param toAdd Generic object to add
     * @return {@link List}
     * @param <T> Generic list type
     */
    public <T> List<T> listWith(final List<T> list, final T toAdd) {
        list.add(toAdd);
        return list;
    }

    /**
     * Retrieves a list without an item
     *
     * @param list     List of generic type
     * @param toRemove Generic object to remove
     * @return {@link List}
     * @param <T> Generic list type
     */
    public <T> List<T> listWithout(final List<T> list, final T toRemove) {
        list.remove(toRemove);
        return list;
    }

    /**
     * List Builder
     *
     * @param <T> Generic List Build type
     */
    public static class ListBuilder<T> {
        private final List<T> initialList;

        /**
         * Default Constructor
         *
         * @param initialList initial list
         */
        private ListBuilder(final List<T> initialList) {
            this.initialList = new ArrayList<>(initialList);
        }

        /**
         * Creates a ListBuilder with a given list
         *
         * @param list Initial List
         * @return {@link ListBuilder}
         * @param <T> Generic type of {@link ListBuilder}
         */
        public static <T> ListBuilder<T> of(final List<T> list) {
            return new ListBuilder<>(list);
        }

        /**
         * Creates a ListBuilder with given array of list
         * @param lists Array of Lists
         * @return {@link ListBuilder}
         * @param <T> Generic type of {@link ListBuilder}
         */
        @SafeVarargs
        public static <T> ListBuilder<T> of(final List<T>... lists) {
            final ListBuilder<T> builder = new ListBuilder<>(new ArrayList<>());

            for (final List<T> list : lists) {
                builder.with(list);
            }
            return builder;
        }

        /**
         * Retrieves a ListBuilder with the given list
         * added.
         *
         * @param with List to add
         * @return {@link ListBuilder}
         */
        public ListBuilder<T> with(final List<T> with) {
            this.initialList.addAll(with);
            return this;
        }

        /**
         * Retrieves the List with all elements of
         * ListBuilder
         *
         * @return List
         */
        public List<T> get() {
            return this.initialList;
        }
    }
}
