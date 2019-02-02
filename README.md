# Android locale example

In this tutorial, I'm going to show you how to set project locale.

![locale](/screenshot.gif)


- Define LocaleManager class like below

```kotlin
class LocaleManager private constructor(val context: Context) {
    var currentLocal: Locale
        private set

    private val PREFS_LANG_KEY = "prefs_theme_key"
    private val DEFAULT_LANG = "en"


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance : LocaleManager? = null

        fun  getInstance(context: Context): LocaleManager {
            if (instance == null)  // NOT thread safe!
                instance = LocaleManager(context)

            return instance!!
        }
    }

    private  var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        val lng = prefs.getString(PREFS_LANG_KEY, DEFAULT_LANG)
        currentLocal = Locale(lng)
    }


    fun setLocale(language: String) {
        val lng = language.toLowerCase()
        currentLocal= Locale(lng)
        prefs.edit().putString(PREFS_LANG_KEY, lng).apply()
    }

    fun wrapContext(): ContextWrapper {

        val language = currentLocal.language

        var ctx = context
        val config = ctx.resources.configuration
        if (language != "") {
            val locale = Locale(language)
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale)
            } else {
                config.locale = locale
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLayoutDirection(locale)
                ctx = ctx.createConfigurationContext(config)
            } else {
                ctx.resources.updateConfiguration(config, ctx.resources.displayMetrics)
            }
        }

        return ContextWrapper(ctx)
    }

}
```

- Wrap the `Activity` Context:

```kotlin
override fun attachBaseContext(newBase: Context?) {
    super.attachBaseContext(LocaleManager.getInstance(newBase!!).wrapContext())
}
```

- Thats it, enjoy it

