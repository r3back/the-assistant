package com.qualityplus.assistant.util.placeholder;

import com.qualityplus.assistant.api.util.IPlaceholder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Placeholder builder
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlaceholderBuilder {
    private final List<IPlaceholder> placeholderList = new ArrayList<>();

    /**
     *
     * @return {@link PlaceholderBuilder}
     */
    public static PlaceholderBuilder create() {
        return new PlaceholderBuilder();
    }

    /**
     *
     * @return {@link PlaceholderBuilder}
     */
    public static PlaceholderBuilder empty() {
        return create(Collections.emptyList());
    }

    /**
     *
     * @param placeholders List of {@link IPlaceholder}
     * @return {@link PlaceholderBuilder}
     */
    public static PlaceholderBuilder create(final List<IPlaceholder> placeholders) {
        return PlaceholderBuilder.create().with(placeholders);
    }

    /**
     *
     * @param placeholders Array of {@link IPlaceholder}
     * @return {@link PlaceholderBuilder}
     */
    public static PlaceholderBuilder create(final IPlaceholder... placeholders) {
        return new PlaceholderBuilder().with(placeholders);
    }

    /**
     *
     * @param placeholders Array of List of {@link IPlaceholder}
     * @return {@link PlaceholderBuilder}
     */
    @SafeVarargs
    public static PlaceholderBuilder create(final List<IPlaceholder>... placeholders) {
        return PlaceholderBuilder.create().with(placeholders);
    }

    /**
     * Add placeholders from list to builder
     *
     * @param placeholders List of {@link IPlaceholder}
     * @return {@link PlaceholderBuilder}
     */
    public PlaceholderBuilder with(final List<IPlaceholder> placeholders) {
        this.placeholderList.addAll(placeholders);
        return this;
    }


    /**
     * Add placeholders from array of placeholder list to builder
     *
     * @param placeholdersArray Array of List of {@link IPlaceholder}
     * @return {@link PlaceholderBuilder}
     */
    @SafeVarargs
    public final PlaceholderBuilder with(final List<IPlaceholder>... placeholdersArray) {
        for (final List<IPlaceholder> placeholders : placeholdersArray) {
            this.placeholderList.addAll(placeholders);
        }
        return this;
    }

    /**
     * Add placeholders from another builder
     *
     * @param placeholders {@link PlaceholderBuilder}
     * @return {@link PlaceholderBuilder}
     */
    public PlaceholderBuilder with(final PlaceholderBuilder placeholders) {
        this.placeholderList.addAll(placeholders.get());
        return this;
    }

    /**
     * Add placeholders from list to builder
     *
     * @param placeholder Array of {@link IPlaceholder}
     * @return {@link PlaceholderBuilder}
     */
    public PlaceholderBuilder with(final IPlaceholder... placeholder) {
        this.placeholderList.addAll(Arrays.asList(placeholder));
        return this;
    }

    /**
     *
     * @return List of {@link IPlaceholder}
     */
    public List<IPlaceholder> get() {
        return this.placeholderList;
    }
}
