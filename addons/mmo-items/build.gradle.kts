repositories{
    maven("https://mvn.lumine.io/repository/maven-public/") {
        name = "Lumine"
    }
    maven("https://nexus.phoenixdevt.fr/repository/maven-public/") {
        name = "MMO Items"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    compileOnly(project(":api"))
    compileOnly(project(":addons:addons-commons"))

    compileOnly("net.Indyuce:MMOItems-API:6.9.5-SNAPSHOT")
    compileOnly("io.lumine:MythicLib-dist:1.6.2-SNAPSHOT")
}