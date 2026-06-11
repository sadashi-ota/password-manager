plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.example.passwordmanager.feature.lock"
  compileSdk = 36
  defaultConfig {
    minSdk = 24
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  buildFeatures {
    compose = true
  }
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:common"))
  implementation(project(":domain:usecase"))

  val composeBom = platform(libs.androidx.compose.bom)
  implementation(composeBom)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.ui.tooling.preview)

  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.activity.compose)

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  implementation(libs.hilt.navigation.compose)

  // Biometrics & Process Lifecycle
  implementation(libs.androidx.biometric)
  implementation(libs.androidx.lifecycle.process)
  implementation(libs.androidx.compose.material.icons.extended)

  // Navigation3
  implementation(libs.androidx.navigation3.runtime)
}
