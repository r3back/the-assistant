repositories {
    maven("https://repo.techscode.com/repository/maven-releases/") {
        name = "TechsCodeAPI"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("me.TechsCode:GradleBasePlugin:4.5.0-BETA")

    compileOnly(project(":addons:regions:regions-commons"))
    compileOnly(project(":addons:addons-commons"))
}