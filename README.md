# feedblocker-android

[![Join the chat at https://gitter.im/eladnava/feedblocker-android](https://badges.gitter.im/eladnava/feedblocker-android.svg)](https://gitter.im/eladnava/feedblocker-android?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Are you spending too much time on feeds? Ever found yourself entranced by the time-sucking, endless Facebook and Instagram feeds, wasting valuable time on distractions? Ever notice you've been doomscrolling for a half an hour without even being aware of it?

FeedBlocker is an Android app for **rooted devices** that limits your feed browsing time by displaying a friendly reminder to close the feed and do something productive for a change, after a configurable amount of time has passed.

## Screenshots

![FeedBlocker](https://raw.github.com/eladnava/feedblocker-android/master/preview/screenshot1.png) 
![Settings](https://raw.github.com/eladnava/feedblocker-android/master/preview/screenshot2.png)

## Root Requirement

This app requires root permissions for the following reasons:

1. To read the device logcat (in order to identify feed open/close events)
2. To close the feed activity (after a friendly reminder is displayed and you agree to close the feed)

## Requirements

* Android Build Tools 23.0.2
* Android API 23 SDK Platform
* Android Studio with Gradle Plugin
* Android Device with Android 2.3 or newer

## Installation

Currently, FeedBlocker is not available on Google Play due to a possible Terms of Service incompatibility. But no matter, you can manually compile and run the app yourself, which is more fun anyway!

1. Clone the project locally - ``git clone https://github.com/eladnava/feedblocker-android.git``
2. Open it in Android Studio
3. Connect your device
4. Build and run the app
5. Grant root access

That's it - FeedBlocker will activate automatically by displaying a popup over the feed when time runs out!

## Feed Compatibility

Currently, only Facebook and Instagram are supported. However, it's relatively easy to support other feeds - create an issue and we'll discuss it!

## Contributing

* If you find a bug or wish to make some kind of change, please create an issue first
* Make your commits as tiny as possible - one feature or bugfix at a time
* Write detailed commit messages, in-line with the project's commit naming conventions
* Make sure your code conventions are in-line with the project

## License

Apache 2.0
