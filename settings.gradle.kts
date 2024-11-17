enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.jpenilla.xyz/snapshots/") {
              name = "JPenilla"
        }
    }
    plugins {
        id("checkstyle")
        id("com.gradleup.shadow") version "8.3.5"
        id("org.cadixdev.licenser") version "0.6.1"
        id("net.kyori.indra") version "3.1.3"
        id("net.kyori.indra.git") version "3.1.3"
        id("net.kyori.indra.publishing") version "3.1.3"
        id("net.kyori.blossom") version "2.1.0"
        id("io.github.patrick.remapper") version "1.4.0"
        id("com.diffplug.spotless") version "6.23.3"
        id("com.github.spotbugs") version "6.0.2"
        id("io.freefair.lombok") version "8.4"
    }
}

plugins {
    id("com.gradle.enterprise") version "3.16"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "the-assistant"

include (
        "addons",
        "api",
        "plugin",
        "nms",
)

setOf("regions", "paster", "paster:paster-commons", "paster:world-edit-6", "paster:world-edit-7",
    "regions:regions-commons", "regions:residence", "regions:ultra-regions",
    "regions:world-guard-6", "regions:world-guard-7", "addons-commons", "economy",
    "mmo-items", "mythic-mobs", "npc", "placeholders", "world-manager", "regions", "paster",
    "paster:paster-commons").forEach { include("addons:${it}") }

setOf("nms-commons", "v1_8_R1", "v1_8_R3", "v1_12_R1", "v1_13_R1",
    "v1_14_R1", "v1_15_R1", "v1_16_R1", "v1_16_R3", "v1_17_R1",
    "v1_18_R1", "v1_18_R2", "v1_19_R1", "v1_19_R2", "v1_19_R3",
    "v1_20_R1", "v1_20_R2", "v1_20_R3", "v1_21_R1").forEach { include("nms:${it}") }


