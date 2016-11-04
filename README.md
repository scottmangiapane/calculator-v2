## Calculator v2

Calculator v2 is an advanced calculator app with functions such as trigonometry, logs, factorials, and more while still maintaining a simple and easy to use interface.

## Download

https://play.google.com/store/apps/details?id=com.scottmangiapane.calculatorv2

## Screenshots

<img src="screenshots/1.png" width="200">
<img src="screenshots/2.png" width="200">
<img src="screenshots/3.png" width="200">

## Build Instructions

* Install the required tools
* Create a new project in Android Studio  
  Package name: com.scottmangiapane.calculatorv2
* Replace the contents of /app/source/main/ with this repo
* Add any libraries to /libs/
* Add any dependencies to /app/build.gradle

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
