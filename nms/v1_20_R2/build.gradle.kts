tasks {
    remap {
        version.set("1.20.2")
    }
}

dependencies {
    //Required for GameProfile
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("com.mojang:brigadier:1.0.18")
    compileOnly("io.netty:netty-all:4.1.90.Final")

    compileOnly("org.spigotmc:spigot:1.20.2-R0.1-SNAPSHOT:remapped-mojang@jar") {
        isTransitive = false
    }
    compileOnly("org.spigotmc:spigot-api:1.20.2-experimental-SNAPSHOT") {
        isTransitive = false
    }

}

