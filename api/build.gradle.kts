dependencies {
    compileOnly("eu.okaeri:okaeri-persistence-mongo:1.5.12")
    compileOnly("com.zaxxer:HikariCP:4.0.3")
    compileOnly("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
    compileOnly("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview.72")
    implementation("com.github.cryptomorin:XSeries:9.9.0")

    //Fix encode URI Problem
    implementation("eu.okaeri:okaeri-commons-core:0.2.17")

    compileOnly(project(":addons:regions:regions-commons"))
    compileOnly(project(":addons:paster:paster-commons"))
    compileOnly(project(":addons:addons-commons"))
}