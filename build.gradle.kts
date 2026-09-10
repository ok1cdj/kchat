// Top-level build file. Toolchain aligned with the sibling Kompakt apps
// (kRadar / kSread): AGP 9 has built-in Kotlin, so the kotlin.android plugin is
// gone; the Compose compiler plugin drives the Kotlin version (>= 2.0.20 to
// consume MMD-android 1.0.2, which also requires compileSdk 35+).
plugins {
    id("com.android.application") version "9.4.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.10" apply false
}
