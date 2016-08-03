## Build Instructions

<ul>
<li>Install the required tools</li>
<li>Create a new project in Android Studio<br>Package name: com.scottmangiapane.calculatorv2</li>
<li>Replace the contents of &lt;project&gt;/app/source/main/ with this repo</li>
<li>Add any libraries to &lt;project&gt;/libs/</li>
<li>Add any dependencies to &lt;project&gt;/app/build.gradle</li>
</ul>

## Required Tools

<ul>
<li>Android Studio</li>
<li>Android Software Development Kit</li>
<li>Java Development Kit</li>
</ul>

## Libraries

<ul>
<li>commons-math3-3.6.1.jar<br>http://commons.apache.org/proper/commons-math/index.html</li>
</ul>

## Dependencies

dependencies {<br>&emsp;compile 'com.android.support:appcompat-v7:24.1.1'<br>&emsp;compile files('commons-math3-3.6.1.jar')<br>}
