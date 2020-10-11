@file:Suppress("SpellCheckingInspection", "unused")

package jp.sadashi.manager.password

object Deps {
    object Versions {
        const val compileSdk = 29
        const val buildTools = "29.0.2"
        const val minSdk = 21
        const val kotlin = "1.3.71"
        const val spek = "2.0.8"
        const val retrofit = "2.6.1"
        const val moshi = "1.8.0"
        const val dagger = "2.24"
    }

    object GradlePlugin {
        const val build = "com.android.tools.build:gradle:4.0.2"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:2.2.0"
        const val versions = "com.github.ben-manes:gradle-versions-plugin:0.28.0"
        const val androidJunit5 = "de.mannodermaus.gradle.plugins:android-junit5:1.6.2.0"
    }

    object Library {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val core = "androidx.core:core-ktx:1.1.0"
        const val material = "com.google.android.material:material:1.0.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:2.2.2"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:2.2.2"
    }

    object Test {
        const val kotlin = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val mockK = "io.mockk:mockk:1.9.3"
        object Spek {
            const val dsl = "org.spekframework.spek2:spek-dsl-jvm:${Versions.spek}"
            const val runn1er = "org.spekframework.spek2:spek-runner-junit5:${Versions.spek}"
        }
    }
}