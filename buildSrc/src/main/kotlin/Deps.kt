@file:Suppress("SpellCheckingInspection", "unused")

package jp.sadashi.manager.password

object Deps {
    object Versions {
        const val compileSdk = 31
        const val minSdk = 26
        const val kotlin = "1.5.31"
        const val spek = "2.0.17"
        const val dagger = "2.30.1"
        const val navigation = "2.3.5"
    }

    object GradlePlugin {
        const val build = "com.android.tools.build:gradle:7.1.1"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
        const val versions = "com.github.ben-manes:gradle-versions-plugin:0.42.0"
        const val androidJunit5 = "de.mannodermaus.gradle.plugins:android-junit5:1.8.2.0"
    }

    object Library {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val core = "androidx.core:core-ktx:1.7.0"
        const val material = "com.google.android.material:material:1.5.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    }

    object Test {
        const val kotlin = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val mockK = "io.mockk:mockk:1.12.2"
        object Spek {
            const val dsl = "org.spekframework.spek2:spek-dsl-jvm:${Versions.spek}"
            const val runn1er = "org.spekframework.spek2:spek-runner-junit5:${Versions.spek}"
        }
    }
}