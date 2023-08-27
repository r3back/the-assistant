package com.qualityplus.assistant.api.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

/**
 * Boolean Utility class
 */
@UtilityClass
public final class BooleanUtil {
    /**
     * Retrieves true, false if string matches or null if
     * string is another thing
     *
     * @param value string value to convert
     * @return Boolean or null
     */
    public @Nullable Boolean fromString(final String value) {
        if (value.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if (value.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        return null;
    }
}
