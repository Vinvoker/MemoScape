package com.example.memoscape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class SettingActivity : AppCompatActivity(), View.OnClickListener, ChangePasswordDialogFragment.OnPasswordChangedListener {

    lateinit var toolbar : Toolbar
    private lateinit var change_password_to_dialog_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.hide()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        change_password_to_dialog_btn = findViewById(R.id.to_change_password_dialog_btn)
        change_password_to_dialog_btn.setOnClickListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_setting_menu -> {
                // Save the settings and finish the activity
                saveSettings()
                finish()
                true
            }
            android.R.id.home -> {
                // Handle the up button by finishing the activity
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveSettings() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.to_change_password_dialog_btn -> {
                val dialog = ChangePasswordDialogFragment()
                dialog.show(supportFragmentManager, "ChangePasswordDialogFragment")
            }
        }
    }

    override fun onPasswordChanged(newPassword: String) {
        TODO("Not yet implemented")
    }

}