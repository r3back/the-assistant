package com.qualityplus.assistant.base.factory;

import com.google.common.collect.ImmutableMap;
import com.qualityplus.assistant.api.nms.NMS;
import com.qualityplus.assistant.api.nms.tab.TabAdapter;
import com.qualityplus.assistant.api.nms.tab.TabHandler;
import com.qualityplus.assistant.base.nms.v1_12_R1_Tab;
import com.qualityplus.assistant.base.nms.v1_16_R1;
import com.qualityplus.assistant.base.nms.v1_16_R3;
import com.qualityplus.assistant.base.nms.v1_17_R1;
import com.qualityplus.assistant.base.nms.v1_17_R1_Tab;
import com.qualityplus.assistant.base.nms.v1_18_R1;
import com.qualityplus.assistant.base.nms.v1_13_R1;
import com.qualityplus.assistant.base.nms.v1_14_R1;
import com.qualityplus.assistant.base.nms.v1_15_R1;
import com.qualityplus.assistant.base.nms.v1_18_R2;
import com.qualityplus.assistant.base.nms.v1_19_R1;
import com.qualityplus.assistant.base.nms.v1_21_R1;
import com.qualityplus.assistant.base.nms.v1_21_R1_Tab;
import com.qualityplus.assistant.base.nms.v1_8_R1;
import com.qualityplus.assistant.base.nms.v1_8_R3;
import com.qualityplus.assistant.base.nms.v1_12_R1;
import com.qualityplus.assistant.base.nms.v1_19_R1_Tab;
import com.qualityplus.assistant.base.nms.v1_19_R2;
import com.qualityplus.assistant.base.nms.v1_19_R2_Tab;
import com.qualityplus.assistant.base.nms.v1_19_R3;
import com.qualityplus.assistant.base.nms.v1_19_R3_Tab;
import com.qualityplus.assistant.base.nms.v1_20_R1;
import com.qualityplus.assistant.base.nms.v1_20_R1_Tab;
import com.qualityplus.assistant.base.nms.v1_20_R2;
import com.qualityplus.assistant.base.nms.v1_20_R2_Tab;
import com.qualityplus.assistant.base.nms.v1_20_R3;
import com.qualityplus.assistant.base.nms.v1_20_R3_Tab;
import com.qualityplus.assistant.base.nms.v1_20_R4;
import com.qualityplus.assistant.base.nms.v1_20_R4_Tab;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * NMS Factory
 */
@Component
public final class NMSFactory {
    private static final Map<String, MinecraftVersion> NEW_NMS_VERSIONS = ImmutableMap.<String, MinecraftVersion>builder()
            .put("1.21.1", MinecraftVersion.V1_21_R1)
            .put("1.20.6", MinecraftVersion.V1_20_R4)
            .build();

    private static final String RECOGNIZED_VERSION_MESSAGE = "Successfully recognized Version %s";
    private static final String UNSUPPORTED_VERSION_MESSAGE = "Unsupported Version %s";
    private static final String DISABLING_PLUGIN_MESSAGE = "Disabling Plugin...";
    private @Inject("injector") OkaeriInjector injector;
    private @Inject Plugin plugin;
    private @Inject Logger logger;

    /**
     * Configure nms version
     *
     * @return {@link NMS}
     */
    @Bean
    public NMS configureNMS() {
        final MinecraftVersion version = getMcVersion();

        if (version.getNms() == null) {
            disablePlugin(version);

            return null;
        } else {
            this.logger.info(String.format(RECOGNIZED_VERSION_MESSAGE, version.name()));

            return this.injector.createInstance(version.getNms().get());
        }
    }

    /**
     * Configure Tab
     *
     * @return {@link TabHandler}
     */
    @Bean
    public TabHandler configureTab() {
        final MinecraftVersion version = getMcVersion();

        if (version.getNms() == null) {
            //disablePlugin(version);

            return null;
        } else {
            this.logger.info(String.format(RECOGNIZED_VERSION_MESSAGE, version.name()));

            final TabAdapter adapter = this.injector.createInstance(version.getTab().get());

            return new TabHandler(adapter, (JavaPlugin) this.plugin, 20);
        }
    }

    /**
     * Retrieves Minecraft Version
     *
     * @return {@link MinecraftVersion}
     */
    private MinecraftVersion getMcVersion() {
        final String version = Bukkit.getServer().getVersion();

        for (final Map.Entry<String, MinecraftVersion> nmsEntry : NEW_NMS_VERSIONS.entrySet()) {
            if (!version.startsWith(nmsEntry.getKey())) {
                continue;
            }
            return nmsEntry.getValue();
        }

        final String nmsVersion = Bukkit.getServer().getClass()
                .getPackage()
                .getName()
                .split("\\.")[3];

        return MinecraftVersion.byName(nmsVersion);
    }

    /**
     * Disable plugin
     *
     * @param version {@link MinecraftVersion}
     */
    private void disablePlugin(final MinecraftVersion version) {
        this.logger.info("Unsupported Version " + version.name());
        this.logger.info("Disabling Plugin...");

        Bukkit.getServer().getPluginManager().disablePlugin(this.plugin);
    }

    /**
     * Minecraft Version representation
     */
    @Getter
    @RequiredArgsConstructor
    public enum MinecraftVersion {
        /**
         * v1_8_R1
         */
        v1_8_R1(() -> v1_8_R1.class, null),
        /**
         * v1_8_R3
         */
        v1_8_R3(() -> v1_8_R3.class, null),
        /**
         * v1_12_R1
         */
        v1_12_R1(() -> v1_12_R1.class, () -> v1_12_R1_Tab.class),
        /**
         * v1_13_R1
         */
        v1_13_R1(() -> v1_13_R1.class, null),
        /**
         * v1_14_R1
         */
        v1_14_R1(() -> v1_14_R1.class, null),
        /**
         * v1_15_R1
         */
        v1_15_R1(() -> v1_15_R1.class, null),
        /**
         * V1_16_R1
         */
        V1_16_R1(() -> v1_16_R1.class, null),
        /**
         * V1_16_R3
         */
        V1_16_R3(() -> v1_16_R3.class, null),
        /**
         * V1_17_R1
         */
        V1_17_R1(() -> v1_17_R1.class, () -> v1_17_R1_Tab.class),
        /**
         * V1_18_R1
         */
        V1_18_R1(() -> v1_18_R1.class, null),
        /**
         * V1_18_R2
         */
        V1_18_R2(() -> v1_18_R2.class, null),
        /**
         * V1_19_R1
         */
        V1_19_R1(() -> v1_19_R1.class, () -> v1_19_R1_Tab.class),
        /**
         * V1_19_R2
         */
        V1_19_R2(() -> v1_19_R2.class, () -> v1_19_R2_Tab.class),
        /**
         * V1_19_R3
         */
        V1_19_R3(() -> v1_19_R3.class, () -> v1_19_R3_Tab.class),
        /**
         * V1_20_R1
         */
        V1_20_R1(() -> v1_20_R1.class, () -> v1_20_R1_Tab.class),
        /**
         * V1_20_R2
         */
        V1_20_R2(() -> v1_20_R2.class, () -> v1_20_R2_Tab.class),
        /**
         * V1_20_R3
         */
        V1_20_R3(() -> v1_20_R3.class, () -> v1_20_R3_Tab.class),
        /**
         * V1_20_R4
         */
        V1_20_R4(() -> v1_20_R4.class, () -> v1_20_R4_Tab.class),
        /**
         * V1_21_R1
         */
        V1_21_R1(() -> v1_21_R1.class, () -> v1_21_R1_Tab.class);


        private final Supplier<Class<? extends NMS>> nms;
        private final Supplier<Class<? extends TabAdapter>> tab;

        /**
         * Retrieves minecraft nms version given a version string
         *
         * @param version version string
         * @return {@link MinecraftVersion}
         */
        public static MinecraftVersion byName(final String version) {
            return Arrays.stream(values())
                    .filter(mcVersion -> mcVersion.name().equalsIgnoreCase(version)).findFirst()
                    .orElse(null);
        }
    }

}
