dependencies {
    //Required for GameProfile
    compileOnly("com.mojang:authlib:1.5.21")

    compileOnly("org.spigotmc:spigot:1.18.1")
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly(project(":nms:nms-commons"))
    implementation("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")
}