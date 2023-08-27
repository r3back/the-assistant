package com.qualityplus.assistant.api.addons.paster.schematic;

import java.io.File;

/**
 * Schematic interface
 */
public interface Schematic {
    /**
     * Retrieves schematic name
     *
     * @return schematic name
     */
    public String getName();

    /**
     * Retrieves schematic file
     *
     * @return {@link File}
     */
    public File getFile();
}
