tasks {
    remap {
        version.set("1.21.3")
    }
}


repositories {
    maven {
        url = uri("https://repo.codemc.io/repository/nms/")
    }

    maven {
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }
}

dependencies {
    //Required for GameProfile
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("com.mojang:brigadier:1.0.18")
    compileOnly("io.netty:netty-all:4.1.90.Final")
    compileOnly("com.mojang:datafixerupper:1.0.20")

    compileOnly("org.spigotmc:spigot:1.21.3-R0.1-SNAPSHOT:remapped-mojang@jar") {
        isTransitive = false
    }
    compileOnly("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT") {
        isTransitive = false
    }

}
