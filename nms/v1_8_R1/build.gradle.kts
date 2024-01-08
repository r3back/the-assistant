group = rootProject.group
version = rootProject.version


dependencies {
    compileOnly("org.spigotmc:spigot:1.8")
    compileOnly(project(":nms:nms-commons"))
    implementation("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")
    implementation("com.github.InventivetalentDev:BossBarAPI:2.4.3-SNAPSHOT")
}