package com.qualityplus.assistant.util.number;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public final class NumberUtil {
    public List<Integer> intStream(final int from, final int to){
        return IntStream.range(from, to).boxed().collect(Collectors.toList());
    }
}
