plugins {
    id("java")
    id("io.github.patrick.remapper")
}

tasks.remap.get().apply {
    archiveClassifier.set("remapped")
}

tasks.named("remap").get().dependsOn("jar")
tasks.named("build").get().dependsOn("remap")

configurations {
    create("remapped") {
        println("File: ${project.buildDir.absolutePath}")

        val resultFile = File(File(project.buildDir, "libs"), "${project.name}-${rootProject.version}-remapped.jar")
        println("resultFile: ${resultFile.name}")

        val files = project.files(resultFile)
        files.builtBy("remap")

        isCanBeResolved = false
        isCanBeConsumed = true

        outgoing.artifact(resultFile)

        dependencies.add(project.dependencies.create(files))
    }
}
