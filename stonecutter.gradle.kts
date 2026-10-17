plugins {
    id("dev.kikugie.stonecutter")
}

// The version the source files currently show. Switch with the
// "Set active project to ..." Gradle tasks; commit with it back on 26.3.
stonecutter active "26.3"

stonecutter parameters {
    replacements {
        // 1.21.11 renamed ResourceLocation to Identifier.
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }
    }
}
