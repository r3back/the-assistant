package com.qualityplus.assistant.util.placeholder;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.api.util.MathUtil;

import java.util.Collections;
import java.util.List;

/**
 * Placeholder implementation
 */
public final class Placeholder implements IPlaceholder {
    private final String key;
    private final String value;
    private List<String> toReplace;

    /**
     * Basic Constructor
     *
     * @param key   placeholder key
     * @param value placeholder value
     */
    public Placeholder(final String key, final String value) {
        this.key = "%" + key + "%";
        this.value = value;
    }

    /**
     * Constructor with int as value
     * @param key   placeholder key
     * @param value placeholder int value
     */
    public Placeholder(final String key, final int value) {
        this.key = "%" + key + "%";
        this.value = String.valueOf(value);
    }

    /**
     * Constructor with double as value
     *
     * @param key   placeholder key
     * @param value placeholder double value
     */
    public Placeholder(final String key, final double value) {
        this.key = "%" + key + "%";
        this.value = MathUtil.round(value);
    }

    /**
     * Constructor for list placeholder
     *
     * @param key   placeholder key
     * @param value placeholder list value
     */
    public Placeholder(final String key, final List<String> value) {
        this.key = "%" + key + "%";
        this.value = "";
        this.toReplace = value;
    }

    @Override
    public final String process(final String line) {
        return line == null ? "" : line.replace(this.key, this.value);
    }

    @Override
    public List<String> processList(final List<String> lore) {
        final int specificIndex = getLine(lore, key);

        if (specificIndex != -1) {
            lore.remove(specificIndex);
            if (toReplace != null && toReplace.size() > 0) {
                lore.addAll(specificIndex, toReplace);
            }
        }

        return lore;
    }

    private int getLine(final List<String> list, final String toCheck) {
        int init = list.indexOf(key);

        if (init != -1) {
            return init;
        }

        for (int i = 0; i<list.size(); i++) {
            if (list.get(i).contains(toCheck)) {
                init = i;
                break;
            }
        }

        return init;
    }

    @Override
    public boolean isListPlaceholder() {
        return toReplace != null;
    }

    /**
     * Creates a list only with this placeholder
     *
     * @return List of {@link IPlaceholder}
     */
    public List<IPlaceholder> alone() {
        return Collections.singletonList(this);
    }
}