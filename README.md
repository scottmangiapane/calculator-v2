## Calculator v2

Calculator v2 is an advanced calculator app with functions such as trigonometry, logs, factorials, and more while still maintaining a simple and easy to use interface.

⚠️ WARNING ⚠️

I made this app a long time ago as a college freshman. Although I'm proud of how it turned out and it _mostly_ works, it [has bugs](https://github.com/scottmangiapane/calculator-v2/issues/2) and could give wrong answers... I don't recommend using it for anything serious. Turns out writing a parser using only string manipulaton then including no test cases is bad.

## Download

https://github.com/scottmangiapane/calculator-v2/releases

## Screenshots

<img src="screenshots/1.png" width="200">
<img src="screenshots/2.png" width="200">
<img src="screenshots/3.png" width="200">

## Build Instructions

* Install the required tools
* Create a new project in Android Studio  
  Package name: com.scottmangiapane.calculatorv2
* Replace the contents of `/app/source/main/` with this repo
* Add any libraries to `/libs/`
* Add any dependencies to `/app/build.gradle`

## Build Requirements

* Android Studio
* Android Software Development Kit
* Java Development Kit

## Libraries

Add this library to the project's `/libs/` folder
* commons-math3-3.6.1.jar  
  http://commons.apache.org/proper/commons-math/index.html

## Dependencies

In the project's `/app/build.gradle` file, add the following dependency.
```groovy
dependencies {
    compile 'com.android.support:appcompat-v7:24.1.1'
    compile files('libs/commons-math3-3.6.1.jar')
}
```
