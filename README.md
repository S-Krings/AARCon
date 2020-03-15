# AARCon
The AARCon (Android-based framework forAugmented Reality with Context-Awareness) aims to ease developing context-aware AR applications in Android by offering a base structure for introducing context-aware adaptations as well as a range of preimplemented classes for context monitoring and adaptation purposes. The framework is available as an Android library written in Java for Google's [ARCore](https://developers.google.com/ar) framework.

## Installation/Setup
- Clone or download AARCon from the master branch's [main page](https://github.com/S-Krings/AARCon) and unpack it if necessary.
- Add the AARConn library folder to your project 
  (e.g. in Android Studio: click "File > New > Import Module", select library directory, then click "Finish")
- List AARCon on top of your project's `settings.gradle` file, as shown here:
  ```
  include ':app', ':aarcon'
  ```
- Add a new line to the `dependencies` in the app module's `build.gradle` file, like in the following code snippet:
  ```
  dependencies {
	implementation project(":aarcon")
  }
  ```
- Click "Sync Project with Gradle Files"
