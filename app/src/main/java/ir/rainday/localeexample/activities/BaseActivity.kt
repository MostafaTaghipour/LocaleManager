package ir.rainday.localeexample.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import ir.rainyday.localemanager.LocaleManager

/**
 * Created by mostafa-taghipour on 12/1/17.
 */

abstract  class BaseActivity : AppCompatActivity(){


    override fun attachBaseContext(newBase: Context) {
        val localeBase = LocaleManager.getInstance().wrapContext(newBase)
        super.attachBaseContext(localeBase)
    }


}
