plugins {
    base
    idea
    id("com.github.johnrengelman.shadow")
    id("maven-publish")
    id("java")
}

group = "com.github.r3back"
version = "3.2.11"

subprojects {
    plugins.apply("java")
    //plugins.apply("checkstyle")
    plugins.apply("maven-publish")

    val mappingModules = setOf("v1_20_R2", "v1_20_R1")

    if (mappingModules.contains(this.name)) {
        plugins.apply("the-assistant-mapping")
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    repositories {
        mavenCentral()
        //NMS Repo

        maven("https://repo.rosewooddev.io/repository/public/") {
            name = "RosewoodDev"
        }
        maven("https://libraries.minecraft.net/") {
            name = "Minecraft versions"
        }
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
            name = "Spigot API"
        }
        maven("https://papermc.io/repo/repository/maven-public/") {
            name = "Paper API"
        }
        maven("https://storehouse.okaeri.eu/repository/maven-public/") {
            name = "Okaeri"
        }
        maven("https://repo.codemc.org/repository/maven-public/") {
            name = "CodeMC NBT API"
        }
        maven("https://jitpack.io") {
            name = "Jitpack"
        }
        maven("https://repo.rapture.pw/repository/maven-snapshots/") {
            name = "SlimeWorld"
        }
        maven("https://repo.rapture.pw/repository/maven-releases/") {
            name = "Rapture"
        }
    }

    dependencies {
        implementation("org.jetbrains:annotations:22.0.0")

        compileOnly("org.projectlombok:lombok:1.18.30")

        // Enable lombok annotation processing
        annotationProcessor("org.projectlombok:lombok:1.18.30")
    }

    /*checkstyle {
        toolVersion = "8.20"
        configFile = file(rootProject.projectDir.getAbsolutePath() + "/config/checkstyle/checkstyle.xml")
        ignoreFailures = false
        showViolations = true
        showViolations = true
        maxWarnings = 0
        sourceSets = [sourceSets.main]
    }*/
}



