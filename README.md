# Pratham-Chikitse

## Problem Statement
During medical emergencies, immediate action is critical. However, many people lack basic first aid knowledge or may panic, forgetting what to do. Additionally, finding the nearest hospital quickly in an unfamiliar location can be a challenge, wasting precious time.

## Solution
Pratham-Chikitse is a comprehensive first aid companion app that provides clear, step-by-step instructions for common emergencies. By offering bilingual support (English and Kannada) and a built-in hospital finder, the app empowers users to provide immediate assistance and find professional medical help efficiently.

## Features
- **Interactive First Aid Guides**: Step-by-step instructions for choking, burns, snake bites, fractures, and heart attacks.
- **Bilingual Support**: Guides are provided in both English and Kannada to reach a wider audience.
- **Hospital Finder**: Quick access to locate nearby medical facilities.
- **Emergency UI**: Simple, card-based interface for quick navigation during high-stress situations.

## Tech Stack
- **Kotlin**: Core logic and activity management.
- **Android Studio**: Official IDE for development.
- **XML / View Binding**: Modern UI layouts and type-safe view interaction.
- **Material Design**: Consistent and intuitive user interface components.

## How to Run
1. Clone the repository.
2. Open in Android Studio.
3. Sync Gradle.
4. Run on emulator or Android device.


## Future Improvements
- **Direct Emergency Calling**: One-tap calling to local emergency services.
- **Offline Mode**: Access to first aid guides without an internet connection.
- **Voice Instructions**: Audio-guided steps for hands-free assistance.
- **Visual Tutorials**: Short animations or videos for procedures like CPR or the Heimlich maneuver.
#### Live Demo APK
https://drive.google.com/file/d/1zoy9uGC-pKYGAEVobNzIMhjqMNJYqYMQ/view?usp=sharing

- ##structure
  PrathamChikitse/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/prathamchikitse/
│   │   │   │   ├── activities/
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── LoginActivity.kt
│   │   │   │   │   └── EmergencyActivity.kt
│   │   │   │   │
│   │   │   │   ├── adapters/
│   │   │   │   │   └── EmergencyAdapter.kt
│   │   │   │   │
│   │   │   │   ├── models/
│   │   │   │   │   └── EmergencyContact.kt
│   │   │   │   │
│   │   │   │   ├── utils/
│   │   │   │   │   └── Constants.kt
│   │   │   │   │
│   │   │   │   └── database/
│   │   │   │       └── AppDatabase.kt
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   ├── layout/
│   │   │   │   ├── mipmap/
│   │   │   │   ├── values/
│   │   │   │   └── menu/
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │
│   ├── build.gradle
│   └── proguard-rules.pro
│
├── gradle/
│
├── screenshots/
│   ├── home.png
│   ├── login.png
│   ├── emergency.png
│   └── dashboard.png
│
├── README.md
├── build.gradle
├── settings.gradle
├── gradle.properties
├── .gitignore
└── LICENSE
