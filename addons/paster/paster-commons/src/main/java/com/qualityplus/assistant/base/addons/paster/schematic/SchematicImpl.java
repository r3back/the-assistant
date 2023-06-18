package com.qualityplus.assistant.base.addons.paster.schematic;

import com.qualityplus.assistant.api.addons.paster.schematic.Schematic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Schematic implementation
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class SchematicImpl implements Schematic {
    private String name;
    private File file;
}
