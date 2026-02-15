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
                    minSdk = 27
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

          packaging {
                    resources {
                              excludes += "/META-INF/{AL2.0,LGPL2.1}"
                              excludes += "META-INF/DEPENDENCIES"
                              excludes += "META-INF/io.netty.versions.properties"
                              excludes += "META-INF/INDEX.LIST"
                              excludes += "arrow-git.properties"
                              excludes += "mozilla/public-suffix-list.txt"
                              excludes += "META-INF/FastDoubleParser-LICENSE"
                              excludes += "META-INF/FastDoubleParser-NOTICE"
                              excludes += "META-INF/thirdparty-LICENSE"
                    }
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

          // OpenCSV
          implementation("com.opencsv:opencsv:5.12.0")


          //ktor
          implementation("io.ktor:ktor-client-core:3.3.3")

          implementation("io.ktor:ktor-client-cio:3.3.3")
          implementation("io.ktor:ktor-client-okhttp:3.3.3")

          implementation("io.ktor:ktor-client-logging:3.3.3")

          implementation("io.ktor:ktor-client-content-negotiation:3.3.3")
          implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.3")
          implementation("io.ktor:ktor-serialization-jackson:3.3.3")
          implementation("io.ktor:ktor-serialization-kotlinx-xml:3.3.3")
          implementation("io.ktor:ktor-serialization-kotlinx-cbor:3.3.3")
          implementation("io.ktor:ktor-serialization-kotlinx-protobuf:3.3.3")

          // Google Credentials
          implementation("androidx.credentials:credentials:1.5.0")

          // Android Auth Library
          implementation("com.google.android.gms:play-services-auth:21.5.0")

          // Google Drive Libraries
          implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
          implementation("com.google.apis:google-api-services-drive:v3-rev20220815-2.0.0")
          implementation("com.google.code.gson:gson:2.13.2")


          // Oauth2
          // Source: https://mvnrepository.com/artifact/com.google.auth/google-auth-library-oauth2-http
          implementation("com.google.auth:google-auth-library-oauth2-http:1.42.1")

          // JSON serialization library, works with the Kotlin serialization plugin
        /*  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
          implementation("com.squareup.retrofit2:converter-kotlinx-serialization:3.0.0")*/
}