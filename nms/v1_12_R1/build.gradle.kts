repositories {
    maven("https://repo.codemc.org/repository/nms/") {
        name = "CodeMC"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.12.2-R0.1-SNAPSHOT") {
        isTransitive = false
    }
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly(project(":nms:nms-commons"))
    implementation("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")
}
