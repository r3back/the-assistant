group = rootProject.group
version = rootProject.version

dependencies {
    compileOnly("org.spigotmc:spigot:1.16.4")
    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly(project(":nms:nms-commons"))
    implementation("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")
}