group = rootProject.group
version = rootProject.version

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
        name = "PlaceholderAPI"
    }
    maven("https://nexus.lucko.me/repository/all/") {
        name = "Lucko"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    compileOnly(project(":api"))
    compileOnly(project(":addons:addons-commons"))

    compileOnly("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")
    compileOnly("be.maximvdw:MVdWPlaceholderAPI:3.0.1-SNAPSHOT") {
        exclude(group = "org.spigotmc")
    }
    compileOnly("me.clip:placeholderapi:2.11.3")
}