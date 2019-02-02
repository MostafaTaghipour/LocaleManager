package ir.rainday.localeexample.activities

import android.app.TaskStackBuilder
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import ir.rainday.localeexample.*
import ir.rainyday.localemanager.AppLocaleChangeReceiver
import ir.rainyday.localemanager.LocaleManager
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*


class SettingActivity : BaseActivity(), AdapterView.OnItemSelectedListener, AppLocaleChangeReceiver.AppLocaleChangeListener {

    private lateinit var localeReceiver: AppLocaleChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val filter = IntentFilter()
        filter.addAction(ir.rainyday.localemanager.LocaleManager.APP_LOCALE_CHANGED_BROADCAST_ACTION)
        this.localeReceiver = AppLocaleChangeReceiver()
        this.localeReceiver.setListener(this)
        registerReceiver(this.localeReceiver, filter)

        spinner.onItemSelectedListener = this
        spinner.adapter = ArrayAdapter<AppLocale>(this, android.R.layout.simple_list_item_1, AppLocale.values())
        LocalePrefrences.getInstance(this).selectedLocale?.let {
            spinner.setSelection(it.value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(this.localeReceiver)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        val selected = (spinner.selectedItem as AppLocale)

        LocalePrefrences.getInstance(this).selectedLocale = selected

        if (selected == AppLocale.System) {
            LocaleManager.getInstance().resetLocale(this)
        } else {
            LocaleManager.getInstance().setCurrentLocale(this, Locale(selected.lang))
        }

    }

    override fun onAppLocaleChanged(newLocale: Locale) {

        val launcherIntent = packageManager.getLaunchIntentForPackage(packageName)
        launcherIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        var settingIntent = Intent(this, SettingActivity::class.java)

        val stack = TaskStackBuilder.create(this)
                .addNextIntent(launcherIntent)
                .addNextIntentWithParentStack(settingIntent)


        finish()
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in)

        stack.startActivities()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // app icon in action bar clicked; go home
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
