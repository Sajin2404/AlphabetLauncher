# Alphabet Launcher

A minimal Android launcher-style application built using Kotlin and Jetpack Compose.

The application provides a black launcher-style home screen with a live clock and date, together with a vertical A–Z alphabet bar on the right side.

When the user touches and drags along the alphabet bar, the selected letter is highlighted and the alphabet bends toward the user's finger. The application list changes according to the selected letter and displays installed launchable applications whose names begin with that letter.

## Features

### Core Features

- Minimal launcher-style home screen
- Live clock
- Live date
- Vertical A–Z alphabet bar
- Star at the top of the alphabet bar
- Dot at the bottom of the alphabet bar
- Real installed application discovery
- Application names and icons
- Alphabetical sorting of applications
- Case-insensitive alphabet filtering
- Selected-letter bubble
- Finger-driven alphabet interaction
- Smooth spring-based letter animation
- Empty state when no applications match a letter
- Tap an application to launch it
- Application list loaded and cached in the ViewModel
- Android 11+ package visibility support

## Technology Stack

- Kotlin
- Jetpack Compose
- Material 3
- AndroidX
- Android PackageManager
- Kotlin Coroutines
- Gradle Kotlin DSL

## Requirements

To build and run this project, you need:

- Android Studio
- Android SDK
- JDK 11 or compatible Android Studio JDK
- Android device or emulator
- USB debugging enabled when using a physical Android device

The application targets Android API 36 and supports devices from Android API 26 onward.

## Build Configuration

| Configuration | Version |
|---|---|
| Kotlin | 2.0.21 |
| Android Gradle Plugin | 9.0.1 |
| Compile SDK | 36.1 |
| Target SDK | 36 |
| Minimum SDK | 26 |
| Java | 11 |
| Jetpack Compose BOM | 2024.09.00 |
| Application Version | 1.0 |

## Project Structure

```text
AlphabetLauncher/
│
├── app/
│   ├── build.gradle.kts
│   │
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   │
│       │   └── java/
│       │       └── com/
│       │           └── sajin/
│       │               └── alphabetlauncher/
│       │                   │
│       │                   ├── MainActivity.kt
│       │                   ├── LauncherViewModel.kt
│       │                   │
│       │                   ├── data/
│       │                   │   ├── AppInfo.kt
│       │                   │   └── AppRepository.kt
│       │                   │
│       │                   ├── ui/
│       │                   │   ├── AlphabetBar.kt
│       │                   │   ├── AppList.kt
│       │                   │   ├── LauncherScreen.kt
│       │                   │   │
│       │                   │   └── theme/
│       │                   │       ├── Color.kt
│       │                   │       ├── Theme.kt
│       │                   │       └── Type.kt
│       │                   │
│       │                   └── utils/
│       │                       └── AlphabetUtils.kt
│       │
│       ├── test/
│       └── androidTest/
│
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── .gitignore
└── README.md
