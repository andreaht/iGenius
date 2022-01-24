# Github App
I've implemented the app trying to follow the provided guidelines. Here there is a recap of my work## Installation

Step by step explanation of how to get a dev environment running.

```
1. To enable Firebase Authentication:
        a. Go to the authentication tab at the Firebase console and enable Github Sign-in methods.
        b. download `google-services.json` and add replace it to the app.
        c. Add your package name and SHA-1 signing-certificate fingerprint.
2. Run the app on your mobile phone or emulator with Google Play Services in it.
```

## Testing

Right click on the `test` or `androidTest` packages and select Run Tests. Testing the app doesn't require the firebase authentication.


## Functionalities
- User login is implemented with Firebase that logs in the user in his github account
- Login requires "repo" permission to get acces to private repository
- After login there is a basic motion layout that implements github icon animation (moves and shrinks)
- A recycler view shows as many cards as many repository are present in the githhub account
- Repository and user info are fetched with retrofit lib and info are stored in a room db, so the user can use the application offlie.
- Cards shows the following info with text and icons: name, private, description (max 3 lines), stars count, language with github color.
- Tapping a repository a bottom sheet dialog is presented with full descriprion and other elements.
- In bottom sheet dialog there is a **custom button that with an antimated view** that stars/unstars the repository.

## Guidelines
- Developed entirerly in Kotlin
- Used coroutines
- Used dagger for DI 
- minsdk 23
- Unit testing and instrumented testing with Dagger, Junit, Espresso and mocked objects.
- Library used: **Retrofit** for github repos and user info and **Glide** to get profile image
