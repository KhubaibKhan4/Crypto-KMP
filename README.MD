# YouTube Clone Kotlin Multiplatform
![Crypto KMP](https://github.com/KhubaibKhan4/Crypto-KMP/blob/master/assests/screenshots/poster.png)

## Overview
Crypto-KMP is a Kotlin Multiplatform (KMP) project aimed at providing cryptocurrency-related functionalities across Android, iOS, Web, and Desktop platforms with shared code. This documentation provides an overview of the project structure, features, and usage instructions.

## 🚀 Future Plans
- TV and Wear OS Versions

## 🌟 Contributions
If you wanna contribute, Please make sure to add new features & Then make a PR.Feel free to contribute to the project and stay tuned for more exciting updates!

# Open To Work
Do you wanna Convert your thoughts into Physicall & Successfull Project Then I'm here for you. I'm open to work, available for Freelance or Remote Work Opportunities. Feel free to reach me out on Email.

## 🤝 Connect with Me

Let's chat about potential projects, job opportunities, or any other collaboration! Feel free to connect with me through the following channels:

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/khubaibkhandev)
[![Twitter](https://img.shields.io/badge/Twitter-Follow-blue?style=for-the-badge&logo=twitter)](https://twitter.com/codespacepro)
[![Email](https://img.shields.io/badge/Email-Drop%20a%20Message-red?style=for-the-badge&logo=gmail)](mailto:18.bscs.803@gmail.com)

If you find my work helpful, you can support me by [![Buy me a coffee](https://img.buymeacoffee.com/button-api/?text=Buy%20me%20a%20coffee&emoji=&slug=khubaibkhan&button_colour=FFDD00&font_colour=000000&font_family=Cookie&outline_colour=000000&coffee_colour=ffffff)](https://www.buymeacoffee.com/khubaibkhan)

## Screen Shots

| ![Screenshot 1](https://github.com/KhubaibKhan4/Crypto-KMP/blob/master/assests/screenshots/1.png) | ![Screenshot 2](https://github.com/KhubaibKhan4/Crypto-KMP/blob/master/assests/screenshots/2.png) | ![Screenshot 3](https://github.com/KhubaibKhan4/Crypto-KMP/blob/master/assests/screenshots/3.png) |
| --- | --- | --- |
| ![Screenshot 4](https://github.com/KhubaibKhan4/Crypto-KMP/blob/master/assests/screenshots/4.png) | 

## Before running!
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
 - install JDK 17 or higher on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`
Run android simulator UI tests: `./gradlew :composeApp:pixel5Check`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`
Run desktop UI tests: `./gradlew :composeApp:jvmTest`

### iOS
To run the application on iPhone device/simulator:
 - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio
Run iOS simulator UI tests: `./gradlew :composeApp:iosSimulatorArm64Test`

### Experimental Browser (JS)
Run the browser application: `./gradlew :composeApp:jsBrowserDevelopmentRun --continue`
Run browser UI tests: `./gradlew :composeApp:jsBrowserTest`

