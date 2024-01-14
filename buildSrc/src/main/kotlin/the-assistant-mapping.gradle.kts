plugins {
    id("java")
    id("io.github.patrick.remapper")
}

repositories.maven("https://repo.codemc.org/repository/nms/")

dependencies.compileOnly(project(":nms:nms-commons"))
dependencies.compileOnly("eu.okaeri:okaeri-platform-bukkit:0.4.0-preview54")

if (setOf("v1_20_R2", "v1_20_R1", "v1_19_R1", "v1_19_R2", "v1_19_R3", "v1_18_R1", "v1_18_R2", "v1_17_R1").contains(project.name)) {

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
