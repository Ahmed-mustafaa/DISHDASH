plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.dishdash"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dishdash"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        dataBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //circularImage
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //lottie animation
    implementation ("com.airbnb.android:lottie:6.5.0")
    //Materrial design
    implementation ("com.google.android.material:material:1.9.0") // Or latest version


}