package ir.rainday.localeexample

import android.content.Context
import android.os.Build
import ir.rainyday.localemanager.LocaleManager
import java.util.*


fun Locale.isRTL(): Boolean {
    return LocaleManager.isRTL(this)
}

fun Locale.isPersian(): Boolean {
    return LocaleManager.isPersian(this)
}

fun Context.isPersian(): Boolean {
    return getCurrentLocale().isPersian()
}
fun Context.isRTL(): Boolean {
    return getCurrentLocale().isRTL()
}

@Suppress("DEPRECATION")
fun Context.getCurrentLocale(): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales.get(0)
    } else {
        resources.configuration.locale
    }
}

