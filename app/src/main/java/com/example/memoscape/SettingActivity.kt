package com.example.memoscape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class SettingActivity : AppCompatActivity() {

    lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_setting_menu -> {
                // Save the settings and finish the activity
                saveSettings()
                finish()
                return true
            }
            android.R.id.home -> {
                // Handle the up button by finishing the activity
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveSettings() {

    }

}