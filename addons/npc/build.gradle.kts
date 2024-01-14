group = rootProject.group
version = rootProject.version

repositories {
    maven("https://mvn.lumine.io/repository/maven-public/") {
        name = "Lumine"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    compileOnly(project(":api"))
    compileOnly(project(":addons:addons-commons"))
}