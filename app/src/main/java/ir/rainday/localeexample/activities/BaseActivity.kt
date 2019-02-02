package ir.rainday.localeexample.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by mostafa-taghipour on 12/1/17.
 */

abstract  class BaseActivity : AppCompatActivity(){


    override fun attachBaseContext(newBase: Context) {
        val localeBase = ir.rainyday.localemanager.LocaleManager.getInstance().wrapContext(newBase)
        super.attachBaseContext(localeBase)
    }


}
