package com.qualityplus.assistant.util.map;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@UtilityClass
public class MapUtils {
    public @NotNull <T, K> Map<T, K> check(final @Nullable Map<T, K> map) {
        return Optional.ofNullable(map)
                .orElse(new HashMap<>());
    }

    public @NotNull <T, K> Map<T, K> checkOr(final @Nullable Map<T, K> map, final Map<T, K> or) {
        return Optional.ofNullable(map)
                .orElse(or);
    }
}
