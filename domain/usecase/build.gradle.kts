plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.example.passwordmanager.domain.usecase"
  compileSdk = 36
  defaultConfig {
    minSdk = 24
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:common"))
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
}
