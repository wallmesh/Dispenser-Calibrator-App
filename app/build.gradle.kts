plugins {
          alias(libs.plugins.android.application)
          alias(libs.plugins.kotlin.android)
          alias(libs.plugins.kotlin.compose)

          // Hilt
          alias(libs.plugins.kotlinAndroidKsp)
          id("com.google.dagger.hilt.android")
}

android {
          namespace = "com.example.dispensercalibrator"
          compileSdk {
                    version = release(36)
          }

          defaultConfig {
                    applicationId = "com.example.dispensercalibrator"
                    minSdk = 24
                    targetSdk = 36
                    versionCode = 1
                    versionName = "1.0"

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
          }

          buildTypes {
                    release {
                              isMinifyEnabled = false
                              proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
          }
          compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
          }
          kotlinOptions {
                    jvmTarget = "21"
          }
          buildFeatures {
                    compose = true
          }
}

dependencies {
          implementation(libs.androidx.core.ktx)
          implementation(libs.androidx.lifecycle.runtime.ktx)
          implementation(libs.androidx.activity.compose)
          implementation(platform(libs.androidx.compose.bom))
          implementation(libs.androidx.compose.ui)
          implementation(libs.androidx.compose.ui.graphics)
          implementation(libs.androidx.compose.ui.tooling.preview)
          implementation(libs.androidx.compose.material3)
          implementation(libs.androidx.navigation.compose)
          implementation(libs.androidx.navigation.runtime.ktx)
          testImplementation(libs.junit)
          androidTestImplementation(libs.androidx.junit)
          androidTestImplementation(libs.androidx.espresso.core)
          androidTestImplementation(platform(libs.androidx.compose.bom))
          androidTestImplementation(libs.androidx.compose.ui.test.junit4)
          debugImplementation(libs.androidx.compose.ui.tooling)
          debugImplementation(libs.androidx.compose.ui.test.manifest)


          // HILT DI
          implementation(libs.hilt.android)
         ksp("com.google.dagger:hilt-android-compiler:2.57.1")
          // for compose DI in Hilt
          implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

          //ROOM
          val room_version = "2.8.4"
          implementation(libs.androidx.room.runtime)
          ksp(libs.androidx.room.compiler)

          // NAVIGATION
          val nav_version = "2.9.6"
          // Navigation- Jetpack Compose integration
          implementation("androidx.navigation:navigation-compose:$nav_version")
          // Views/Fragments integration
          implementation("androidx.navigation:navigation-fragment:$nav_version")
          implementation("androidx.navigation:navigation-ui:$nav_version")
          // Feature module support for Fragments
          implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
          // Testing Navigation
          androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
}