repositories{
    maven("https://mvn.lumine.io/repository/maven-public/") {
        name = "Lumine"
    }
}

dependencies {
    //Slime World
    //compileOnly("com.flowpowered:flow-nbt:2.0.2")
    //compileOnly("com.grinderwolf:slimeworldmanager-plugin:2.7.0-SNAPSHOT")
    //compileOnly("com.grinderwolf:slimeworldmanager-api:2.7.0-SNAPSHOT")

    compileOnly("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")

    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    compileOnly(project(":api"))
    compileOnly(project(":addons:addons-commons"))
}