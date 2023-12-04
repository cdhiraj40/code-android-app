object Android {
    const val compileSdkVersion = 34
    const val minSdkVersion = 22
    const val targetSdkVersion = 33
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val buildToolsVersion = "33.0.1"
}

object Versions {
    const val java = "17"
    const val kotlin = "1.9.10"
    const val kotlinx_coroutines = "1.7.3"
    const val android_gradle_build_tools = "8.1.0"
    const val google_services = "4.3.15"

    const val androidx_core = "1.12.0"
    const val androidx_constraint_layout = "2.1.3"
    const val androidx_lifecycle = "2.6.2"
    const val androidx_navigation = "2.7.4"
    const val androidx_browser = "1.4.0"
    const val androidx_material = "1.5.0"
    const val androidx_room = "2.4.2"
    const val sqlcipher = "4.5.1@aar"

    const val compose = "1.4.3"
    const val compose_activities: String = "1.4.0"
    const val compose_view_models: String = "2.6.2"
    const val compose_navigation: String = "2.7.3"

    const val hilt = "2.48.1"
    const val hilt_jetpack = "1.1.0-beta01"
    const val okhttp = "4.9.3"
    const val rxjava: String = "3.1.3"
    const val rxandroid: String = "3.0.0"

    const val compose_accompanist: String = "0.24.2-alpha"
    const val kin_sdk: String = "1.0.1"
    const val grpc_android: String = "1.33.1"
    const val slf4j: String = "1.7.25"
    const val firebase_bom: String = "29.1.0"
    const val crashlytics_gradle: String = "2.8.1"
    const val play_service_auth = "20.4.0"
    const val play_service_auth_phone = "18.0.1"

    const val grpc_okhttp: String = "1.33.1"
    const val grpc_kotlin: String = "1.0.0"

    const val mp_android_chart: String = "v3.1.0"
    const val lib_phone_number_port: String = "8.12.43"
    const val lib_phone_number_google: String = "8.12.54"
    const val hilt_nav_compose: String = "1.1.0-alpha02"
    const val qr_generator: String = "1.0.4"
    const val zxing: String = "3.3.2"

    const val androidx_test_runner = "1.4.0"
    const val junit = "4.13.1"
    const val androidx_junit = "1.1.3"
    const val espresso = "3.4.0"
    const val mixpanel = "6.4.0"

    const val markwon = "4.6.2"
    const val timber = "5.0.1"
}

object Classpath {
    const val android_gradle_build_tools = "com.android.tools.build:gradle:${Versions.android_gradle_build_tools}"
    const val kotlin_hilt_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val androidx_navigation_safeargs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidx_navigation}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val google_services = "com.google.gms:google-services:${Versions.google_services}"

    const val crashlytics_gradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics_gradle}"
    const val bugsnag = "com.bugsnag:bugsnag-android-gradle-plugin:7.+"
    const val firebase_perf = "com.google.firebase:perf-plugin:1.4.2"
    const val secrets_gradle_plugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1"
}

object Plugins {
    const val android_application = "com.android.application"
    const val android_library = "com.android.library"
    const val androidx_navigation_safeargs = "androidx.navigation.safeargs.kotlin"
    const val kotlin_android = "kotlin-android"
    const val kotlin_parcelize = "kotlin-parcelize"
    const val kotlin_jvm = "org.jetbrains.kotlin.jvm"
    const val kotlin_kapt = "kotlin-kapt"
    const val hilt = "dagger.hilt.android.plugin"
    const val google_services = "com.google.gms.google-services"
    const val firebase_crashlytics = "com.google.firebase.crashlytics"
    const val firebase_perf = "com.google.firebase.firebase-perf"
    const val bugsnag = "com.bugsnag.android.gradle"
    const val secrets_gradle_plugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
}

object Libs {
    const val androidx_core = "androidx.core:core-ktx:${Versions.androidx_core}"
    const val androidx_constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraint_layout}"
    const val androidx_lifecycle_runtime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifecycle}"
    const val androidx_navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.androidx_navigation}"
    const val androidx_navigation_ui =
        "androidx.navigation:navigation-ui-ktx:${Versions.androidx_navigation}"
    const val androidx_browser = "androidx.browser:browser:${Versions.androidx_browser}"
    const val android_material =
        "com.google.android.material:material:${Versions.androidx_material}"
    const val androidx_room_runtime = "androidx.room:room-runtime:${Versions.androidx_room}"
    const val androidx_room_rxjava3 = "androidx.room:room-rxjava3:${Versions.androidx_room}"
    const val androidx_room_compiler = "androidx.room:room-compiler:${Versions.androidx_room}"
    const val sqlcipher = "net.zetetic:android-database-sqlcipher:${Versions.sqlcipher}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_android_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hilt_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt_jetpack}"
    const val hilt_android_test = "com.google.dagger:hilt-android-testing:${Versions.hilt}"

    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinx_coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines}"
    const val kotlinx_coroutines_test =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinx_coroutines}"
    const val kotlinx_collections_immutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6"


    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttp_logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val androidx_constraint_layout_compose =
        "androidx.constraintlayout:constraintlayout-compose:1.0.0"
    //const val androidx_lifecycle_compose = androidx.lifecycle-viewmodel-compose:${Versions.androidx_lifecycle}"

    const val compose_ui = "androidx.compose.ui:ui:1.5.3"
    const val compose_ui_tools = "androidx.compose.ui:ui-tooling:1.5.3"
    const val compose_ui_tools_preview =
        "androidx.compose.ui:ui-tooling-preview:1.5.3"
    const val compose_foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val compose_material = "androidx.compose.material:material:${Versions.compose}"
    const val compose_activities =
        "androidx.activity:activity-compose:${Versions.compose_activities}"
    const val compose_view_models =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.compose_view_models}"
    const val compose_livedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val compose_navigation =
        "androidx.navigation:navigation-compose:${Versions.compose_navigation}"

    const val rxjava = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
    const val rxandroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxandroid}"

    const val slf4j = "org.slf4j:slf4j-android:${Versions.slf4j}"
    const val grpc_android = "io.grpc:grpc-android:${Versions.grpc_android}"
    const val kin_sdk = "org.kin.sdk.android:base:${Versions.kin_sdk}"
    const val compose_accompanist =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.compose_accompanist}"

    const val firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
    const val firebase_analytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebase_crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebase_messaging = "com.google.firebase:firebase-messaging-ktx"
    const val firebase_perf = "com.google.firebase:firebase-perf-ktx"

    const val play_service_auth = "com.google.android.gms:play-services-auth:${Versions.play_service_auth}"
    const val play_service_auth_phone = "com.google.android.gms:play-services-auth-api-phone:${Versions.play_service_auth_phone}"

    const val grpc_okhttp = "io.grpc:grpc-okhttp:${Versions.grpc_okhttp}"
    const val grpc_kotlin = "io.grpc:grpc-kotlin-stub-lite:${Versions.grpc_kotlin}"

    const val inject = "javax.inject:javax.inject:1"

    const val mp_android_chart = "com.github.PhilJay:MPAndroidChart:${Versions.mp_android_chart}"
    const val lib_phone_number_port = "io.michaelrocks:libphonenumber-android:${Versions.lib_phone_number_port}"
    const val lib_phone_number_google = "com.googlecode.libphonenumber:libphonenumber:${Versions.lib_phone_number_google}"
    const val hilt_nav_compose = "androidx.hilt:hilt-navigation-compose:1.1.0-alpha01"
    const val qr_generator = "androidmads.library.qrgenearator:QRGenearator:${Versions.qr_generator}"
    const val zxing = "com.google.zxing:core:${Versions.zxing}"

    const val androidx_test_runner =
        "androidx.test:runner:${Versions.androidx_test_runner}"
    const val androidx_junit =
        "androidx.test.ext:junit:${Versions.androidx_junit}"
    const val junit =
        "junit:junit:${Versions.junit}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val espresso_intents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    const val mixpanel = "com.mixpanel.android:mixpanel-android:${Versions.mixpanel}"

    const val markwon_core = "io.noties.markwon:core:${Versions.markwon}"
    const val markwon_linkify = "io.noties.markwon:linkify:${Versions.markwon}"
    const val markwon_ext_strikethrough = "io.noties.markwon:ext-strikethrough:${Versions.markwon}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val bugsnag = "com.bugsnag:bugsnag-android:5.+"
}
