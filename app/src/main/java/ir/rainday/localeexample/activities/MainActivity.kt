package ir.rainday.localeexample.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ir.rainday.localeexample.R

class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title=getString(R.string.main)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId== R.id.action_settings)
            startActivity(Intent(this, SettingActivity::class.java))

        return super.onOptionsItemSelected(item)
    }




}
