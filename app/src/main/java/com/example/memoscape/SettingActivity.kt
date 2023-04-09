package com.example.memoscape

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar

class SettingActivity : AppCompatActivity(), View.OnClickListener, ChangePasswordDialogFragment.OnPasswordChangedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var emailUpdateSwitch: SwitchCompat
    private lateinit var changePasswordToDialogBtn: Button
    private lateinit var twoFAlayout: LinearLayout
    // theme radio button plisss dont forget
    private lateinit var deleteMyAccount: Button

    private val recipients = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.hide()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        emailUpdateSwitch = findViewById(R.id.email_notification_switch)
        emailUpdateSwitch.setOnClickListener(this)

        changePasswordToDialogBtn = findViewById(R.id.to_change_password_dialog_btn)
        changePasswordToDialogBtn.setOnClickListener(this)

        twoFAlayout = findViewById(R.id.two_fa_layout)
        twoFAlayout.setOnClickListener(this)

        // about THEME Radio Group

        deleteMyAccount = findViewById(R.id.delete_acc_btn)
        deleteMyAccount.setOnClickListener(this)


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
            R.id.email_notification_switch -> {
                val switch = v as SwitchCompat
                val isChecked = switch.isChecked

                // Perform necessary actions based on the switch state
                if (isChecked) {
                    // Handle switch on
                    Log.d("MyTag","Switch is ON")

                    // The real code
//                    recipients.add(User.email)
                    recipients.add("recipient1@example.com")

                } else {
                    // Handle switch off
                    Log.d("MyTag","Switch is OFF")

                    // The real code
//                    recipients.remove(User.email)
                    recipients.remove("recipient1@example.com")
                }
            }
            R.id.to_change_password_dialog_btn -> {
                val dialog = ChangePasswordDialogFragment()
                dialog.show(supportFragmentManager, "ChangePasswordDialogFragment")
            }
            R.id.two_fa_layout -> {
                Log.d("MyTag", "Clicked the 2FA Layout")
            }
            R.id.delete_acc_btn -> {
                Log.d("MyTag", "Clicked delete my account Button")
                val intent = Intent(this@SettingActivity, DeleteMyAccountActivity::class.java)
                startActivity(intent)
            }
        }

//        // Pass the updated list of recipient emails to the EmailSenderService
//        val intent = Intent(context, EmailSenderService::class.java)
//        intent.putExtra("recipientEmails", recipientEmails.toTypedArray())
//        val pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
//        val triggerTime = SystemClock.elapsedRealtime() + 10000
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)


    }

    override fun onPasswordChanged(newPassword: String) {
        TODO("Not yet implemented")
    }

}