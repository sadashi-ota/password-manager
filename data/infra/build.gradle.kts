plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.example.passwordmanager.data.infra"
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
  implementation(project(":core:database"))
  implementation(project(":core:crypto"))
  implementation(project(":domain:usecase"))
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
}
