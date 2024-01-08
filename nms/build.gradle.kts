group = rootProject.group
version = rootProject.version

allprojects {
    dependencies {
        compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
        compileOnly(project(":api"))
    }
}