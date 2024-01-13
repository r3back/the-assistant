group = rootProject.group
version = rootProject.version

tasks {
    remap {
        version.set("1.20.2")
    }
}

repositories {
    maven("https://repo.codemc.org/repository/nms/") {
        name = "CodeMC"
    }
}

dependencies {
    //Required for GameProfile
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("com.mojang:brigadier:1.0.18")
    compileOnly("io.netty:netty-all:4.1.90.Final")

    compileOnly("org.spigotmc:spigot:1.20-R0.1-SNAPSHOT:remapped-mojang@jar") {
        isTransitive = false
    }
    compileOnly("org.spigotmc:spigot-api:1.20-R0.1-SNAPSHOT") {
        isTransitive = false
    }
    compileOnly(project(":nms:nms-commons"))
    implementation("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")
}