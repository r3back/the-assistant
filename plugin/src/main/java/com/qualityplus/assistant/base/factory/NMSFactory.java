package com.qualityplus.assistant.base.factory;

import com.qualityplus.assistant.api.nms.NMS;
import com.qualityplus.assistant.base.nms.*;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Bean;
import eu.okaeri.platform.core.annotation.Component;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * NMS Factory
 */
@Component
public final class NMSFactory {
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
        final String nmsVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        final MinecraftVersion version = MinecraftVersion.byName(nmsVersion);

        final String toSend = nmsVersion
                .replace("v", "")
                .replace("_R1", "")
                .replace("_R2", "")
                .replace("_R3", "")
                .replace("_", ".");

        if (version.getNms() == null) {
            this.logger.info(String.format(UNSUPPORTED_VERSION_MESSAGE, toSend));
            this.logger.info(DISABLING_PLUGIN_MESSAGE);

            Bukkit.getServer().getPluginManager().disablePlugin(this.plugin);

            return null;
        } else {
            this.logger.info(String.format(RECOGNIZED_VERSION_MESSAGE, toSend));

            return this.injector.createInstance(version.getNms());
        }
    }

    /**
     * Minecraft Version representation
     */
    @RequiredArgsConstructor
    public enum MinecraftVersion {
        /**
         * v1_8_R1
         */
        v1_8_R1(() -> v1_8_R1.class),
        /**
         * v1_8_R3
         */
        v1_8_R3(() -> v1_8_R3.class),
        /**
         * v1_12_R1
         */
        v1_12_R1(() -> v1_12_R1.class),
        /**
         * v1_13_R1
         */
        v1_13_R1(() -> v1_13_R1.class),
        /**
         * v1_14_R1
         */
        v1_14_R1(() -> v1_14_R1.class),
        /**
         * v1_15_R1
         */
        v1_15_R1(() -> v1_15_R1.class),
        /**
         * V1_16_R1
         */
        V1_16_R1(() -> v1_16_R1.class),
        /**
         * V1_16_R3
         */
        V1_16_R3(() -> v1_16_R3.class),
        /**
         * V1_17_R1
         */
        V1_17_R1(() -> v1_17_R1.class),
        /**
         * V1_18_R1
         */
        V1_18_R1(() -> v1_18_R1.class),
        /**
         * V1_18_R2
         */
        V1_18_R2(() -> v1_18_R2.class),
        /**
         * V1_19_R1
         */
        V1_19_R1(() -> v1_19_R1.class),
        /**
         * V1_19_R2
         */
        V1_19_R2(() -> v1_19_R2.class),
        /**
         * V1_19_R3
         */
        V1_19_R3(() -> v1_20_R1.class),
        /**
         * V1_20_R1
         */
        V1_20_R1(() -> v1_20_R1.class);

        private final Supplier<Class<? extends NMS>> nmsSupplier;

        /**
         * Retrieves supplier content
         *
         * @return Class of {@link NMS}
         */
        public Class<? extends NMS> getNms() {
            return nmsSupplier.get();
        }

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
