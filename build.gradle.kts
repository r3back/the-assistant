plugins {
    base
    idea
    id("com.github.johnrengelman.shadow")
    id("maven-publish")
    id("checkstyle")
    id("java")
}

group = "com.github.r3back"
version = "3.18.0"

ext.set("mojangUnmappedVersions", setOf("1_21_R4", "1_21_R3", "1_21_R2", "1_21_R1", "v1_20_R4", "v1_20_R3","v1_20_R2", "v1_20_R1", "v1_19_R1", "v1_19_R2", "v1_18_R1", "v1_18_R2", "v1_17_R1"))

subprojects {
    group = rootProject.group
    version = rootProject.version

    plugins.apply("java")
    plugins.apply("checkstyle")
    plugins.apply("maven-publish")

    val regex = """v\d_[0-9]+_R\d""".toRegex()

    if (regex.containsMatchIn(this.name)) {
        plugins.apply("the-assistant-mapping")
    }

    repositories {
        mavenCentral()
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
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "papermc"
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
        maven("https://nexus.lucko.me/repository/all/") {
            name = "Lucko"
        }
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "PaperReleases"
        }
    }

    dependencies {
        implementation("org.jetbrains:annotations:22.0.0")
        compileOnly("org.projectlombok:lombok:1.18.30")

        // Enable lombok annotation processing
        annotationProcessor("org.projectlombok:lombok:1.18.30")
    }

    val projectSourceSets = sourceSets
    val checkStyleFile = "${rootProject.projectDir.absolutePath}/config/checkstyle/checkstyle.xml"

    checkstyle {
        toolVersion = "8.20"
        configFile = file(checkStyleFile)
        config = resources.text.fromFile(checkStyleFile)
        isIgnoreFailures = false
        isShowViolations = true
        isShowViolations = true
        maxWarnings = 0
        sourceSets = mutableListOf(projectSourceSets.main.get())
    }

    tasks {
        build {
            dependsOn(checkstyleMain)
        }
    }
}



