repositories {
    maven("https://nexus.lucko.me/repository/all/") {
        name = "Lucko"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("org.black_ixx:playerpoints:3.1.0")
    compileOnly("net.milkbowl.vault:VaultAPI:1.7")
    compileOnly ("com.github.Realizedd:TokenManager:3.2.4") {
        isTransitive = false
    }

    compileOnly(project(":api"))
    compileOnly(project(":addons:addons-commons"))
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("RoyaleEconomyAPI.jar"))))
}