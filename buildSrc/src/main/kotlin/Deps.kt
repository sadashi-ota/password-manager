@file:Suppress("SpellCheckingInspection", "unused")

package jp.sadashi.manager.password

object Deps {
    object Versions {
        const val compileSdk = 31
        const val minSdk = 26
        const val kotlin = "1.5.31"
        const val spek = "2.0.17"
        const val dagger = "2.30.1"
        const val compose = "1.0.5"
    }

    object GradlePlugin {
        const val build = "com.android.tools.build:gradle:7.1.1"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val versions = "com.github.ben-manes:gradle-versions-plugin:0.42.0"
        const val androidJunit5 = "de.mannodermaus.gradle.plugins:android-junit5:1.8.2.0"
    }

    object Library {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val core = "androidx.core:core-ktx:1.7.0"
        const val material = "com.google.android.material:material:1.5.0"

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val uiTool = "androidx.compose.ui:ui-tooling:${Versions.compose}"
            const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
            const val activity = "androidx.activity:activity-compose:1.3.1"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
            const val navigation = "androidx.navigation:navigation-compose:2.4.1"

            object Material {
                const val core = "androidx.compose.material:material:${Versions.compose}"
                const val icons = "androidx.compose.material:material-icons-core:${Versions.compose}"
                const val iconsExt = "androidx.compose.material:material-icons-extended:${Versions.compose}"
            }

            object Accompanist {
                const val animation = "com.google.accompanist:accompanist-navigation-animation:0.24.3-alpha"
            }
        }
    }

    object Test {
        const val kotlin = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val mockK = "io.mockk:mockk:1.12.2"

        object Spek {
            const val dsl = "org.spekframework.spek2:spek-dsl-jvm:${Versions.spek}"
            const val runner = "org.spekframework.spek2:spek-runner-junit5:${Versions.spek}"
        }
    }
}