repositories{
    maven("https://maven.enginehub.org/repo/") {
        name = "EngineHub"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.1")

    compileOnly(project(":addons:regions:regions-commons"))
    compileOnly(project(":addons:addons-commons"))
}