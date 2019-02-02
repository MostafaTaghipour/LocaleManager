package ir.rainday.localeexample

import android.content.Context
import android.content.SharedPreferences

class LocalePrefrences(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: LocalePrefrences? = null

        fun getInstance(context: Context): LocalePrefrences {
            return INSTANCE ?: synchronized(this) {
                LocalePrefrences(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    private var lngPreferences: SharedPreferences = context.getSharedPreferences("LANG_PREF", Context.MODE_PRIVATE)

    var selectedLocale: AppLocale?
        get() {
            val i = lngPreferences.getInt("LOCALE_KEY", -1)
            if (i != -1) {
                return AppLocale.fromValue(i)
            }
            return null
        }
        set(value) {

            value?.let {
                lngPreferences.edit().apply {
                    putInt("LOCALE_KEY", it.value)
                    apply()
                }
            }

        }

}
