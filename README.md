# AndroidLocaleManager

[![](https://jitpack.io/v/MostafaTaghipour/localemanager.svg)](https://jitpack.io/#MostafaTaghipour/localemanager)

## [iOS version is here](https://github.com/MostafaTaghipour/mtpLocaleManager)

AndroidLocaleManger is a locale manager for Android:

- Change locale at runtime
- Supports multiple language
- Change locale according to system locale
- Easy to use


![multi-language app](https://raw.githubusercontent.com/MostafaTaghipour/LocaleManager/master/screenshots/1.gif)


## Requirements

- Api 14+

## Installation

Add JitPack to repositories in your project's root `build.gradle` file:

```Gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your module's `build.gradle` file:

```Gradle
dependencies {
    ...
    implementation 'com.github.MostafaTaghipour:localemanager:1.0.0'
}
```


## Usage

- Wrap the `Activity` Context:

```kotlin
override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(LocaleManager.getInstance().wrapContext(newBase))
}
```

- Any time you need to change the locale of the application using the following code

```kotlin
LocaleManager.getInstance().setCurrentLocale(this /*context*/, Locale("fa" /* your desired language*/))
```

- Thats it, enjoy it


### BroadcastReceiver
There is a BroadcastReceiver that fired when app locale did changed
```kotlin
private lateinit var localeReceiver: AppLocaleChangeReceiver

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    //register BroadcastReceiver
    val filter = IntentFilter()
    filter.addAction(LocaleManager.APP_LOCALE_CHANGED_BROADCAST_ACTION)
    this.localeReceiver = AppLocaleChangeReceiver()
    this.localeReceiver.setListener(this)
    registerReceiver(this.localeReceiver, filter)
}

override fun onAppLocaleChanged(newLocale: Locale) {
    // app locale changed
}

override fun onDestroy() {
    super.onDestroy()
    //unregister BroadcastReceiver
    unregisterReceiver(this.localeReceiver)
}
```

## Author

Mostafa Taghipour, mostafa@taghipour.me

## License

AndroidLocaleManager is available under the MIT license. See the LICENSE file for more info.
