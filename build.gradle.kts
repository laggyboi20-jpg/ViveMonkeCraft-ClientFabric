plugins {
    // Applies the correct Loom variant for the Minecraft version being built.
    id("dev.kikugie.loom-back-compat")
}

val mcVersion = sc.current.version
version = "${property("mod.version")}-$mcVersion-Release"
group = "laggyboi"
base.archivesName = property("mod.archive") as String

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    else -> JavaVersion.VERSION_21
}

repositories {
    // Mod Menu (the "Mods" screen + config button)
    maven("https://maven.terraformersmc.com/releases/")
    // Cloth Config (the slider/toggle widgets used to build the config screen)
    maven("https://maven.shedaniel.me/")
}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    // Mojang mappings on obfuscated versions; nothing on 26.1+.
    loomx.applyMojangMappings()

    // Use mod* configurations on every version: loom-back-compat converts them on 26.1+.
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    // The mod talks to Vivecraft/QuestCraft purely by reflection (VivecraftBridge),
    // so it needs no compile-time VR API.

    // Optional: the in-game config screen via Mod Menu + Cloth Config.
    modImplementation("com.terraformersmc:modmenu:${property("deps.modmenu")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    modImplementation("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
}

loom {
    runConfigs.all {
        preferGradleTask = true
        runDirectory = rootProject.file("run") // Shared between versions
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava
    toolchain {
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

tasks {
    processResources {
        val props = mapOf(
            "version" to project.version.toString(),
            "minecraft_version" to mcVersion,
            "loader_version" to project.property("deps.fabric_loader").toString(),
        )
        inputs.properties(props)
        filesMatching("fabric.mod.json") { expand(props) }
    }

    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = requiredJava.majorVersion.toInt()
    }

    withType<Jar> {
        from(rootProject.file("LICENSE")) { rename { "${it}_ViveMonkeCraft" } }
    }

    // Builds every version's jar and copies them to build/libs/<mod version>/.
    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the mod jar and copies it to the root build/libs/<mod version>/"
        from(loomx.modJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs/${project.property("mod.version")}"))
    }
}
