group = rootProject.group
version = rootProject.version

dependencies {
    //Required for GameProfile
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("com.mojang:brigadier:1.0.18")
    compileOnly("io.netty:netty-all:4.1.90.Final")

    compileOnly("org.spigotmc:spigot:1.19.3")
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly(project(":nms:nms-commons"))
    implementation("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")
}