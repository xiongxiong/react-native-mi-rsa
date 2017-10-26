
# react-native-mi-rsa

## Getting started

`$ npm install react-native-mi-rsa --save`

### Mostly automatic installation

`$ react-native link react-native-mi-rsa`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-mi-rsa` and add `RNMiRsa.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNMiRsa.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNMiRsaPackage;` to the imports at the top of the file
  - Add `new RNMiRsaPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-mi-rsa'
  	project(':react-native-mi-rsa').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-mi-rsa/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-mi-rsa')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNMiRsa.sln` in `node_modules/react-native-mi-rsa/windows/RNMiRsa.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Mi.Rsa.RNMiRsa;` to the usings at the top of the file
  - Add `new RNMiRsaPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNMiRsa from 'react-native-mi-rsa';

// TODO: What to do with the module?
RNMiRsa;
```
  