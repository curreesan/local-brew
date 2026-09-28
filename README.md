# Local Brew

A café-discovery Android app built with Kotlin and Jetpack Compose. Find nearby cafés, search by
name, save favorites, and view details — backed by a real MVVM architecture (Compose UI →
ViewModel → Repository → Retrofit/Room/Firestore).

## Features

- Discover nearby cafés using device location (Geoapify Places API)
- Search cafés by name
- Save/unsync favorites (Firebase Firestore, per signed-in user)
- Email/password authentication (Firebase Auth)
- Offline fallback: last-fetched café list is cached locally (Room) and served if the network
  call fails
- Custom app theme and hand-converted vector icon set

## Tech stack

- **UI:** Jetpack Compose, Material 3, Navigation Compose
- **Architecture:** MVVM (ViewModel + Repository layer), manual dependency injection via a
  singleton `Graph` object
- **Network:** Retrofit + Gson (Geoapify Places API)
- **Local storage:** Room (café cache)
- **Backend:** Firebase Authentication, Cloud Firestore (favorites)

## Setup

This project needs two things that are intentionally **not** committed to this repo:

1. **`app/google-services.json`** — your own Firebase project config. Create a Firebase project,
   register an Android app with package name `ree.selfcode.localbrew`, enable Authentication
   (Email/Password) and Cloud Firestore, then download `google-services.json` into `app/`.
2. **`local.properties`** — add your own Geoapify API key (free tier at
   [geoapify.com](https://www.geoapify.com/)):
   ```properties
   GEOAPIFY_API_KEY=your_key_here
   ```

## Build

```
./gradlew assembleDebug
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`.
