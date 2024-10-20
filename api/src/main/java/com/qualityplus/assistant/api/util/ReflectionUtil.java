package com.qualityplus.assistant.api.util;

import lombok.experimental.UtilityClass;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

/**
 * Reflection utility class
 */
@UtilityClass
public final class ReflectionUtil {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    /**
     * Get Setter
     *
     * @param clazz clazz type
     * @param name  name
     * @return {@link MethodHandle}
     */
    public static MethodHandle getSetter(final Class<?> clazz, final String name) {
        try {
            return LOOKUP.unreflectSetter(getField(clazz, name));
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get field
     *
     * @param clazz     clazz type
     * @param fieldName field name
     * @return {@link Field}
     */
    public static Field getField(final Class<?> clazz, final String fieldName) {
        if (clazz == null) {
            return null;
        }
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (final NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
