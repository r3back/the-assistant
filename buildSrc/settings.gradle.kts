pluginManagement {
    repositories {
        mavenLocal()
        maven("https://maven.wagyourtail.xyz/snapshots/") {
            name = "Wagyourtail snapshots"
        }
        maven("https://maven.wagyourtail.xyz/releases") {
            name = "Wagyourtail releases"
        }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.gradleup.shadow") version "8.3.5"
    }
}


rootProject.name = "the-assistant-buildsrc"
