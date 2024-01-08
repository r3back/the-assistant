group = rootProject.group
version = rootProject.version

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    compileOnly(project(":addons:regions:regions-commons"))
    compileOnly(project(":addons:addons-commons"))
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}