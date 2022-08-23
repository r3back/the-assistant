package com.qualityplus.assistant.util.placeholder;

import com.qualityplus.assistant.api.util.IPlaceholder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlaceholderBuilder {
    private final List<IPlaceholder> placeholderList = new ArrayList<>();

    public static PlaceholderBuilder create(){
        return new PlaceholderBuilder();
    }

    public static PlaceholderBuilder create(List<IPlaceholder> placeholders){
        return PlaceholderBuilder.create().with(placeholders);
    }

    public static PlaceholderBuilder create(IPlaceholder... placeholders){
        return new PlaceholderBuilder().with(placeholders);
    }

    public PlaceholderBuilder with(List<IPlaceholder> placeholders){
        placeholderList.addAll(placeholders);
        return this;
    }

    public PlaceholderBuilder with(PlaceholderBuilder placeholders){
        placeholderList.addAll(placeholders.get());
        return this;
    }

    public PlaceholderBuilder with(IPlaceholder... placeholder){
        placeholderList.addAll(Arrays.stream(placeholder).collect(Collectors.toList()));
        return this;
    }

    public List<IPlaceholder> get(){
        return placeholderList;
    }
}
