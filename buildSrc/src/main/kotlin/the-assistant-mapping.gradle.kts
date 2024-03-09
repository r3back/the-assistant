@file:Suppress("UNCHECKED_CAST")
plugins {
    id("java")
    id("io.github.patrick.remapper")
}

repositories.maven("https://repo.codemc.org/repository/nms/")

dependencies.compileOnly(project(":nms:nms-commons"))
dependencies.compileOnly("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")

val mojangUnmappedVersions: Set<String> = rootProject.ext.get("mojangUnmappedVersions") as Set<String>

if (mojangUnmappedVersions.contains(project.name)) {

    tasks.named("remap").get().dependsOn("jar")
    tasks.named("build").get().dependsOn("remap")

    configurations {
        create("remapped") {
            val resultFile = File(File(project.buildDir, "libs"), "${project.name}-${project.version}-remapped.jar")

            val files = project.files(resultFile)
            files.builtBy("remap")

            isCanBeResolved = false
            isCanBeConsumed = true

            outgoing.artifact(resultFile)

            dependencies.add(project.dependencies.create(files))
        }
    }
    tasks.remap.get().apply {
        archiveClassifier.set("remapped")
    }
}
