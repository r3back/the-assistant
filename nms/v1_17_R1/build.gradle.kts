tasks {
    remap {
        version.set("1.17")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.17-R0.1-SNAPSHOT:remapped-mojang@jar") {
        isTransitive = false
    }
    compileOnly("org.spigotmc:spigot-api:1.17-R0.1-SNAPSHOT")
}