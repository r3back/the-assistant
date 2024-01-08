import com.gradle.scan.agent.serialization.scan.serializer.kryo.it

plugins {
    id ("java-library")
    id ("com.github.johnrengelman.shadow")
    alias(libs.plugins.runpaper)
}

group = rootProject.group
version = rootProject.version

val okaeriDependencies = setOf("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview.72",
    "eu.okaeri:okaeri-configs-json-simple:4.0.1",
    "eu.okaeri:okaeri-persistence-redis:1.5.12",
    "eu.okaeri:okaeri-persistence-jdbc:1.5.12",
    "eu.okaeri:okaeri-persistence-mongo:1.5.12")

val driverDependencies = setOf("com.zaxxer:HikariCP:4.0.3",
    "org.mariadb.jdbc:mariadb-java-client:2.7.3",
    "com.h2database:h2:2.1.214",
    "mysql:mysql-connector-java:8.0.27",
    "org.mongodb:mongodb-driver-sync:4.6.1")

val relocateMap = mutableMapOf("com.cryptomorin.xseries" to "com.qualityplus.assistant.lib.com.cryptomorin.xseries",
    "i18n-platform-commands" to "com.qualityplus.assistant.lib.i18n-platform-commands",
    "eu.okaeri" to "com.qualityplus.assistant.lib.eu.okaeri",
    "com.zaxxer.hikari" to "com.qualityplus.assistant.lib.com.zaxxer.hikari",
    /*"io.netty": "com.qualityplus.assistant.lib.io.netty",*/
    "org.json.simple" to "com.qualityplus.assistant.lib.org.json.simple",
    "org.reactivestreams" to "com.qualityplus.assistant.lib.org.reactivestreams",
    "reactor" to "com.qualityplus.assistant.lib.reactor",
    "org.slf4j" to "com.qualityplus.assistant.lib.org.slf4j",
    "org.h2" to "com.qualityplus.assistant.lib.org.h2",
    "org.mariadb.jdbc" to "com.qualityplus.assistant.lib.org.mariadb.jdbc",
    "org.intellij.lang.annotations" to "com.qualityplus.assistant.lib.org.intellij.lang.annotations",
    "org.inventivetalent" to "com.qualityplus.assistant.lib.org.inventivetalent",
    "xyz.xenondevs.particle" to "com.qualityplus.assistant.lib.xyz.xenondevs.particle",
    "org.jetbrains.annotations" to "com.qualityplus.assistant.lib.org.jetbrains.annotations",
    "de.tr7zw" to "com.qualityplus.assistant.lib.de.tr7zw",
    "com.mysql" to "com.qualityplus.assistant.lib.com.mysql",
    "com.mongodb" to "com.qualityplus.assistant.lib.com.mongodb"/*,
                        "io.lettuce": "com.qualityplus.assistant.lib.io.lettuce",
                        "io.bson": "com.qualityplus.assistant.lib.io.bson"*/
)

val copyJars = {
        file: Provider<RegularFile> -> run {
            val name = file.get().asFile.name

            val folder = rootProject.rootDir.absolutePath

            val path = "$folder/all-generated/$name"
            val testPath = "$folder/test-suite/mc-config/plugins/$name"

            file.get().asFile.copyTo(File(path), true)
            file.get().asFile.copyTo(File(testPath), true)
        }
}

repositories {
    //TODO CHECK THIS
    maven("org.spigotmc:") {
        name = "Spigot"
    }
    maven("https://repo.dmulloy2.net/repository/public/") {
        name = "Dmulloy2 ProtocolLib"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
    implementation("eu.okaeri:okaeri-commons-core:0.2.21")
    implementation("com.github.cryptomorin:XSeries:9.4.0")
    implementation("com.comphenix.protocol:ProtocolLib:4.7.0")
    implementation("com.github.InventivetalentDev:BossBarAPI:2.4.3-SNAPSHOT")
    implementation("com.github.r3back:fast-try:0.0.6")
    //implementation "io.lettuce:lettuce-core:6.1.1.RELEASE"
    //implementation "org.mongodb:mongo-java-driver:3.12.10"

    implementation(project(":api"))
    implementation(project(":nms"))


    val addonsModules = project(":addons").dependencyProject.subprojects
    val nmsModules = project(":nms").dependencyProject.subprojects
    addonsModules.forEach { implementation(it) }
    nmsModules.forEach {
        run {
            if (it.name == "v1_20_R2") {

                print("Nombre: " + it.name)


                implementation(project(":nms:${it.name}", "remapped"))
            } else {
                implementation(it)
            }
        }
    }

    //implementation(project(":nms:v1_20_R2", "remapped"))

    okaeriDependencies.forEach{ implementation (it) }
    driverDependencies.forEach{ implementation (it) }

    implementation("de.tr7zw:item-nbt-api:2.11.3")
    implementation("xyz.xenondevs:particle:1.8.4")

    implementation("org.slf4j:slf4j-nop:2.0.5")
}


tasks {
    shadowJar {
        archiveClassifier.set("")

        dependencies {
            //include("com.qualityplus.helper:.*")
            include(dependency("com.h2database:.*"))
            include(dependency("com.github.cryptomorin:.*"))
            include(dependency("i18n-platform-commands:.*"))
            include(dependency("eu.okaeri:.*"))
            include(dependency("org.json.simple:.*"))
            include(dependency("org.mariadb.jdbc:.*"))
            include(dependency("com.zaxxer:.*"))
            include(dependency("org.slf4j:.*"))
            include(dependency("com.googlecode.json-simple:.*"))
            include(dependency("org.jetbrains:.*"))
            include(dependency("de.tr7zw:.*"))
            include(dependency("xyz.xenondevs:.*"))
            //include(dependency("org.mongodb:.*"))
            //include(dependency("io.lettuce:lettuce-core:.*"))

            include(dependency("org.mongodb:mongodb-driver-sync:.*"))
            include(dependency("com.github.InventivetalentDev:BossBarAPI:.*"))
            include(dependency("mysql:.*"))

            include(project(":api"))
            include(project(":addons"))
            include(project(":addons:world-manager"))
            include(project(":addons:addons-commons"))
            include(project(":addons:economy"))
            include(project(":addons:npc"))
            include(project(":addons:mmo-items"))
            include(project(":addons:placeholders"))
            include(project(":addons:mythic-mobs"))

            include(project(":addons:paster"))
            include(project(":addons:paster:paster-commons"))
            include(project(":addons:paster:world-edit-6"))
            include(project(":addons:paster:world-edit-7"))
            include(project(":addons:regions"))
            include(project(":addons:regions:regions-commons"))
            include(project(":addons:regions:residence"))
            include(project(":addons:regions:ultra-regions"))
            include(project(":addons:regions:world-guard-6"))
            include(project(":addons:regions:world-guard-7"))

            include(project(":nms"))
            include(project(":nms:nms-commons"))
            include(project(":nms:v1_8_R1"))
            include(project(":nms:v1_8_R3"))
            include(project(":nms:v1_12_R1"))
            include(project(":nms:v1_13_R1"))
            include(project(":nms:v1_14_R1"))
            include(project(":nms:v1_15_R1"))
            include(project(":nms:v1_16_R1"))
            include(project(":nms:v1_16_R3"))
            include(project(":nms:v1_17_R1"))
            include(project(":nms:v1_18_R1"))
            include(project(":nms:v1_18_R2"))
            include(project(":nms:v1_19_R1"))
            include(project(":nms:v1_19_R2"))
            include(project(":nms:v1_19_R3"))
            include(project(":nms:v1_20_R1"))
            include(project(":nms:v1_20_R2"))
        }

        relocateMap.forEach { k, v -> relocate(k, v)}

        doLast {
            @Suppress("UNCHECKED_CAST")
            (copyJars as? ((Provider<RegularFile>) -> File) ?: return@doLast)(archiveFile)
        }
    }
}


publishing {
    repositories {
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
            credentials {
                username = System.getenv("JITPACK_USERNAME")
                password = System.getenv("JITPACK_PASSWORD")
            }
        }
    }

    publications {
        register<MavenPublication>("maven") {
            groupId = project.group.toString()
            version = project.version.toString()
            artifactId = rootProject.name

            artifact(project.tasks.shadowJar.get().archiveFile)
        }
    }
}