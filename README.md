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
MainActivity.kt

MainActivity is the entry point of the application.

It:

Creates the Android Activity
Initializes the LauncherViewModel
Enables edge-to-edge display
Starts the Jetpack Compose UI
Applies the AlphabetLauncher theme
LauncherViewModel.kt

LauncherViewModel manages the main launcher state.

It is responsible for:

Loading installed applications
Keeping the application list in memory
Maintaining the selected alphabet letter
Maintaining the dragging state
Filtering applications by letter
Launching applications

The application list is loaded when the ViewModel is initialized.

Application discovery is performed using the IO dispatcher so that it does not block the main UI thread.

AppInfo.kt

AppInfo is the data model representing a launchable application.

It contains:

name
packageName
icon
AppRepository.kt

AppRepository handles installed application discovery and application launching.

It uses Android's PackageManager with:

Intent.ACTION_MAIN
Intent.CATEGORY_LAUNCHER

This identifies applications that expose a launcher activity.

The applications are:

Converted into AppInfo objects
Deduplicated by package name
Sorted alphabetically by application name

The repository also creates a launch intent when an application is selected.

LauncherScreen.kt

LauncherScreen controls the main launcher interface.

It displays:

Current time
Current date
Home content
Selected letter
Filtered application list
Alphabet bar

The clock and date are updated every second.

When the alphabet is not being dragged, the home content is displayed.

When dragging starts, the selected letter and matching application list are displayed.

AlphabetBar.kt

AlphabetBar implements the custom A–Z interaction and curve animation.

It contains:

A–Z letters
Touch and drag handling
Selected letter bubble
Horizontal letter displacement
Spring animations
Star indicator
Bottom dot
AppList.kt

AppList displays applications matching the selected letter.

Each application row contains:

Application icon
Application name

Tapping an application launches it.

If no applications match the selected letter, the following empty state is displayed:

No apps
AlphabetUtils.kt

AlphabetUtils.kt converts the finger's Y-coordinate into an alphabet index.

The index is then converted into a letter from A to Z.

Installed Application Discovery

The launcher reads the real list of launchable applications installed on the Android device.

The application creates an intent using:

Intent(Intent.ACTION_MAIN).apply {
    addCategory(Intent.CATEGORY_LAUNCHER)
}

The Android PackageManager returns the matching launcher activities.

Each activity is converted into an AppInfo object containing:

Application name
Package name
Application icon

The applications are sorted alphabetically using a case-insensitive comparison.

Android 11+ Package Visibility

Android 11 and newer versions introduced restrictions on applications querying information about other installed applications.

The project therefore declares the launcher intent in AndroidManifest.xml:

<queries>
    <intent>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent>
</queries>

This allows the application to discover launchable applications through PackageManager.

Alphabet Filtering

When the user selects a letter, the application filters the cached application list.

For example, when the selected letter is:

G

applications whose names begin with G are displayed, such as:

Gmail
Google
Google Maps
Google Photos

The comparison is case-insensitive.

The filtering operates on the cached application list instead of querying PackageManager again.

Curve Animation

The alphabet curve is implemented directly using Jetpack Compose.

No third-party curve or animation library is used for the main alphabet interaction.

When the user touches and drags along the A–Z bar, the finger's vertical position is converted into an alphabet index.

The selected letter becomes the center of the curve.

Letters close to the selected letter move further toward the center of the screen, while letters farther away move less.

This produces the curved/bulging effect around the user's finger.

Conceptually:

Normal state:

A
B
C
D
E
F
G
H
I
J


During interaction:

A
B
C
  D
     E
        F
           G
        H
     I
  J

The horizontal movement is calculated using the distance between each letter and the selected letter.

A Gaussian-style falloff is used:

influence =
    e^(-distance² / (2 × sigma²))

The influence is multiplied by a maximum horizontal displacement.

Therefore:

Closer to selected letter
        ↓
Greater displacement

Farther from selected letter
        ↓
Smaller displacement

This produces the smooth curved appearance around the selected letter.

Spring Animation

The letters use Jetpack Compose's spring animation API.

Example:

animateFloatAsState(
    targetValue = targetShift,
    animationSpec = spring(
        dampingRatio = 0.8f,
        stiffness = 600f
    )
)

The spring animation allows the letters to move smoothly between their current and target positions.

When dragging stops, the target displacement becomes zero, causing the alphabet letters to return toward their original straight-line positions.

Touch Interaction

The alphabet bar handles touch and drag gestures.

During a drag:

Finger position
      |
      v
Y coordinate
      |
      v
Alphabet index
      |
      v
Selected letter
      |
      v
Application filtering
      |
      v
Curve animation

The selected letter changes as the finger moves up and down the alphabet bar.

Selected Letter Bubble

The selected letter is displayed inside a circular white bubble.

For example:

      G
     ( )

The selected letter is enlarged compared with the other letters so that it is easy to identify while dragging.

Empty Letters

If no installed application begins with the selected letter, the application displays a clear empty state:

No apps

This prevents the screen from appearing blank.

Application Launching

Applications displayed in the list are clickable.

When the user taps an application:

The package name is retrieved.
PackageManager.getLaunchIntentForPackage() is used.
A launch intent is created.
The selected application is opened.

The flow is:

User taps application
          |
          v
Package name
          |
          v
PackageManager
          |
          v
Launch Intent
          |
          v
Application opens
Application Caching and Performance

The application list is loaded once when the LauncherViewModel is created.

The result is stored in memory.

The flow is:

Cached application list
          |
          v
Selected letter
          |
          v
Filter cached list
          |
          v
Display applications

The touch interaction does not repeatedly query PackageManager.

This keeps the touch path lightweight and allows the alphabet animation to remain responsive.

Home Screen

The resting screen displays:

Current time
Current date
Favourites heading
Short instruction
A–Z alphabet bar

The home content is displayed while the user is not dragging the alphabet.

When dragging begins, the home content is replaced by the selected letter and matching application list.

When the finger is released, the selected letter is cleared and the home content returns.

Current Time and Date

The application updates the displayed time and date once per second.

The time is displayed using:

HH:mm

The date is displayed using:

EEE, dd MMM yyyy

The values are recalculated every second while the screen is active.

Gradle Setup

The project uses Gradle Kotlin DSL.

The Gradle version catalog is located at:

gradle/libs.versions.toml

The project uses:

Android Gradle Plugin: 9.0.1
Kotlin: 2.0.21
Compose BOM: 2024.09.00
Setup
1. Clone the Repository
git clone https://github.com/YOUR_USERNAME/AlphabetLauncher.git

Replace YOUR_USERNAME with your GitHub username.

2. Open the Project

Open the cloned project in Android Studio.

AlphabetLauncher

Allow Android Studio to load the Gradle project.

3. Gradle Sync

Android Studio should automatically start Gradle Sync.

If it does not, select:

File → Sync Project with Gradle Files

Wait until synchronization completes successfully.

4. Connect an Android Device

For testing on a physical Android phone:

Enable Developer Options.
Enable USB Debugging.
Connect the phone to the computer.
Accept the USB debugging authorization request.
Select the device in Android Studio.
5. Run the Application

Select the app configuration.

Click:

Run ▶

Android Studio will build and install the application on the selected device.

Command-Line Build
Windows
gradlew.bat assembleDebug
Linux/macOS
./gradlew assembleDebug

The generated debug APK will be located in:

app/build/outputs/apk/debug/
Libraries and Dependencies

The following libraries are declared in the project's Gradle version catalog.

Library	Version	Purpose
AndroidX Core KTX	1.18.0	Kotlin extensions and core Android APIs
AndroidX Lifecycle Runtime KTX	2.10.0	Lifecycle-aware Android functionality
AndroidX Activity Compose	1.13.0	Integrates Jetpack Compose with Android Activity
Jetpack Compose BOM	2024.09.00	Manages compatible Compose library versions
Jetpack Compose UI	BOM managed	Core Jetpack Compose UI framework
Jetpack Compose UI Graphics	BOM managed	Graphics-related Compose functionality
Jetpack Compose UI Tooling Preview	BOM managed	Compose preview support
Jetpack Compose Material 3	BOM managed	Material 3 components and theming
JUnit	4.13.2	Unit testing
AndroidX Test JUnit	1.3.0	Android instrumentation testing
Espresso Core	3.7.0	Android UI testing
Compose UI Test JUnit4	BOM managed	Compose UI testing
Compose UI Tooling	BOM managed	Compose debugging and tooling
Compose UI Test Manifest	BOM managed	Compose UI test configuration

The main alphabet curve animation does not use a third-party animation library. The curve and letter movement are implemented using Jetpack Compose animation APIs.

Testing Checklist
Home Screen
 Application starts successfully
 Current time is displayed
 Current date is displayed
 A–Z alphabet bar is visible
 Star is visible at the top
 Dot is visible at the bottom
Alphabet Interaction
 Touching the alphabet selects a letter
 Dragging vertically changes the selected letter
 Letters move toward the selected letter
 Selected letter becomes enlarged
 Selected letter appears in a circular bubble
 Curve follows the finger
 Letters return toward their original positions after release
Application Discovery
 Installed launchable applications are discovered
 Application icons are displayed
 Application names are displayed
 Applications are sorted alphabetically
 Android 11+ package visibility works correctly
Filtering
 Selecting a letter filters the application list
 Filtering is case-insensitive
 Correct applications are displayed
 Letter with no matching applications displays No apps
Launching
 Tapping an application opens it
 Correct application is launched
Performance
 Application discovery happens outside the touch path
 Application list is cached
 Alphabet interaction remains responsive
AI-Assisted Development

AI tools were used during development as a development and debugging aid.

They were used for:

Understanding the assignment requirements
Planning the project structure
Generating initial implementation guidance
Debugging Kotlin and Jetpack Compose compilation errors
Understanding Android PackageManager
Debugging package and theme configuration
Refining the alphabet touch interaction
Preparing project documentation

The implementation was reviewed and tested during development.

Commit History

The repository keeps the development history instead of squashing the project into a single commit.

The commit history documents the development process, including implementation, debugging, and refinement of the launcher application.

Limitations

The current implementation focuses on the core launcher requirements.

The following optional features are not currently implemented:

Setting the application as the default Android launcher
Haptic feedback
Swipe-up search
Persistent custom favourites
Automatic updates when applications are installed or uninstalled
Hiding or dimming empty alphabet letters
Dedicated unit tests for alphabet grouping
Dedicated unit tests for touch-position mapping

These features can be added as future improvements.

Future Improvements

Possible future improvements include:

Registering the application as a default Android launcher
Adding haptic feedback when the selected letter changes
Improving spring physics and interaction feel
Adding swipe-up application search
Adding persistent favourite applications
Monitoring application installation and removal
Dimming letters that have no matching applications
Adding unit tests for alphabet utilities
Improving accessibility support
Further optimizing icon loading and rendering
Author

Sajin Jayachandran

Android Developer Assignment
Alphabet Launcher
