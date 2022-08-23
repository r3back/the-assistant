package com.qualityplus.dragon.base.service;

import com.qualityplus.assistant.api.addons.paster.schematic.Schematic;
import com.qualityplus.assistant.base.addons.paster.schematic.SchematicImpl;
import com.qualityplus.dragon.api.service.SchematicService;
import com.qualityplus.dragon.base.configs.Config;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public final class SchematicServiceImpl implements SchematicService {
    private final List<Schematic> schematics = new ArrayList<>();
    private @Inject Config config;
    private @Inject Plugin plugin;
    private @Inject Logger logger;

    @Override
    @Delayed(time = MinecraftTimeEquivalent.SECOND)
    public void load() {
        Arrays.stream(getSchematicsFolder().listFiles()).iterator().forEachRemaining(this::loadSchematic);

        if (getSchematic(config.eventSettings.schematicSettings.id).isPresent()) return;

        logger.warning(String.format("Schematic %s wasn't found in plugins/TheDragon/schematics!", config.eventSettings.schematicSettings.id));
    }

    private File getSchematicsFolder(){
        JavaPlugin javaPlugin = (JavaPlugin) plugin;

        File schematicsFolder = new File(javaPlugin.getDataFolder() + "/schematics", "");

        schematicsFolder.mkdirs();

        return schematicsFolder;
    }

    private void loadSchematic(File file){
        String fileName = file.getName();

        if(fileName.contains(".schematic") || fileName.contains(".schem")){
            String name = fileName.replace(".schematic", "").replace(".schem", "");

            schematics.add(new SchematicImpl(name, file));

            logger.info(String.format("Successfully found %s schematic!", name));
        }
    }

    @Override
    public Optional<Schematic> getSchematic(String name){
        return schematics.stream().filter(schematic -> schematic.getName().equalsIgnoreCase(name)).findFirst();
    }
}
