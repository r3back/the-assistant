repositories{
    maven("https://maven.enginehub.org/repo/") {
        name = "EngineHub"
    }
}

dependencies {
    compileOnly ("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    compileOnly ("com.sk89q.worldedit:worldedit-bukkit:7.2.0-SNAPSHOT") { isTransitive = false}
    compileOnly ("com.sk89q.worldedit:worldedit-core:7.2.0-SNAPSHOT") { isTransitive = false}

    implementation ("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview.72")

    compileOnly (project(":addons:paster:paster-commons"))
    compileOnly (project(":addons:addons-commons"))
}