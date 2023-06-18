package com.qualityplus.assistant.util.list;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
