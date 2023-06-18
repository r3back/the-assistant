package com.qualityplus.assistant.api.util;

import java.util.List;

/**
 * Placeholder interface
 */
public interface IPlaceholder {
    /**
     *
     * @param line line to process
     * @return Line parsed
     */
    public String process(final String line);

    /**
     *
     * @param line List of lines to process
     * @return List of lines parsed
     */
    public List<String> processList(final List<String> line);

    /**
     *
     * @return true if is list placeholder
     */
    public boolean isListPlaceholder();
}
