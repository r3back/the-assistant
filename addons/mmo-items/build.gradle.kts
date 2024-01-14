repositories{
    maven("https://mvn.lumine.io/repository/maven-public/") {
        name = "Lumine"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    compileOnly(project(":api"))
    compileOnly(project(":addons:addons-commons"))

    compileOnly("net.Indyuce:MMOItems:6.7.1-SNAPSHOT")
    compileOnly("io.lumine:MythicLib-dist:1.4")
}