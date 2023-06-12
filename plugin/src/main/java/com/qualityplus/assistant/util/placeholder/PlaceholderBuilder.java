package com.qualityplus.assistant.util.placeholder;

import com.qualityplus.assistant.api.util.IPlaceholder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlaceholderBuilder {
    private final List<IPlaceholder> placeholderList = new ArrayList<>();

    public static PlaceholderBuilder create() {
        return new PlaceholderBuilder();
    }

    public static PlaceholderBuilder empty() {
        return create(Collections.emptyList());
    }

    public static PlaceholderBuilder create(final List<IPlaceholder> placeholders) {
        return PlaceholderBuilder.create().with(placeholders);
    }

    public static PlaceholderBuilder create(final IPlaceholder... placeholders) {
        return new PlaceholderBuilder().with(placeholders);
    }


    @SafeVarargs
    public static PlaceholderBuilder create(final List<IPlaceholder>... placeholders) {
        return PlaceholderBuilder.create().with(placeholders);
    }


    public PlaceholderBuilder with(final List<IPlaceholder> placeholders) {
        placeholderList.addAll(placeholders);
        return this;
    }



    @SafeVarargs
    public final PlaceholderBuilder with(final List<IPlaceholder>... placeholdersArray) {
        for (final List<IPlaceholder> placeholders : placeholdersArray) {
            placeholderList.addAll(placeholders);
        }
        return this;
    }

    public PlaceholderBuilder with(final PlaceholderBuilder placeholders) {
        placeholderList.addAll(placeholders.get());
        return this;
    }

    public PlaceholderBuilder with(final IPlaceholder... placeholder) {
        placeholderList.addAll(Arrays.asList(placeholder));
        return this;
    }

    public List<IPlaceholder> get() {
        return placeholderList;
    }


}
