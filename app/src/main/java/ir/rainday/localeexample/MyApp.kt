package ir.rainday.localeexample

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import ir.rainyday.localemanager.LocaleManager
import java.util.*


/**
 * Created by mostafa-taghipour on 10/31/17.
 */

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
            private set

        val appContext: Context
            get() = LocaleManager.getInstance().wrapContext(instance)
    }

    private val osLocaleChangeReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
           if (LocalePrefrences.getInstance(context).selectedLocale == AppLocale.System){
               LocaleManager.getInstance().resetLocale(context)
           }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //set Language
        LocalePrefrences.getInstance(this).selectedLocale?.let {
            val locale = if (it == AppLocale.System) {
                LocaleManager.getOperatingSystemLocale()
            } else {
                Locale(it.lang)
            }
            LocaleManager.getInstance().setCurrentLocale(this, locale)
        }


        // receive os locale change event
        val filter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
        registerReceiver(osLocaleChangeReceiver, filter)
    }


}

