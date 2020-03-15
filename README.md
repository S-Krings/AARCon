# AARCon
The AARCon (Android-based framework forAugmented Reality with Context-Awareness) aims to ease developing context-aware AR applications in Android by offering a base structure for introducing context-aware adaptations as well as a range of preimplemented classes for context monitoring and adaptation purposes. The framework is available as an Android library written in Java for Google's [ARCore](https://developers.google.com/ar) framework.

## Installation/Setup
- Clone or download AARCon from the master branch's [main page](https://github.com/S-Krings/AARCon) and unpack it if necessary.
- Add the AARConn library folder to your project
  (e.g. in Android Studio: click "File > New > Import Module", select library directory, then click "Finish").
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
- Click "Sync Project with Gradle Files".

## Usage
### Components
AARCon has three main components, which are responsible for monitoring context and reacting to detected context changes. They will be introduced here.
#### Condition
- responsible for obtaining information about the different context features
- `abstract`, has to be extended with a subclass for each context feature that is supposed to be monitored
- contains organisational methods for internal communication which the subclasses inherit

#### Rule
- responsible for executing adaptations (in response to context changes monitored by conditions)
- `abstract`, has to be extended with a subclass for each kind of action that is supposed to be executed- 
- contains organisational methods for internal communication which the subclasses inherit
- execution is dependant of one or more conditions, which have to be added to the rule

#### Control
- responsible for continous context monitoring and quick reaction to changes
- registers and observes all rules and conditions and updates them regularly
- is not supposed to be extended, please use as is
