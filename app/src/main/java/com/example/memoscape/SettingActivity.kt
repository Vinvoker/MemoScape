package com.example.memoscape

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import java.sql.SQLException
import java.sql.Statement

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var emailUpdateSwitch: SwitchCompat
    private lateinit var changePasswordToDialogBtn: Button
    private lateinit var twoFAlayout: LinearLayout
    private lateinit var logoutLayout: RelativeLayout
    private lateinit var deleteMyAccount: Button

    private val sharedPref by lazy {
        getSharedPreferences("email_notification_switcher", MODE_PRIVATE)
    }

    private val dbConnection = DatabaseConnection()
    private val connection = dbConnection.createConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.apply {
            setTitle(resources.getString(R.string.notification_and_setting_title))
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@SettingActivity, R.color.light_grey)))
        }

        emailUpdateSwitch = findViewById(R.id.email_notification_switch)
        emailUpdateSwitch.isChecked = CurrentUser.getGetUpdates()
//        emailUpdateSwitch.isChecked = sharedPref.getBoolean("switch_state", false)
        emailUpdateSwitch.setOnClickListener(this)
        emailUpdateSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("switch_state", isChecked).apply()
        }

        changePasswordToDialogBtn = findViewById(R.id.to_change_password_dialog_btn)
        changePasswordToDialogBtn.setOnClickListener(this)

        twoFAlayout = findViewById(R.id.two_fa_layout)
        twoFAlayout.setOnClickListener(this)

        logoutLayout = findViewById(R.id.logout_layout)
        logoutLayout.setOnClickListener(this)

        deleteMyAccount = findViewById(R.id.delete_acc_btn)
        deleteMyAccount.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.email_notification_switch -> {
                val switch = v as SwitchCompat
                val isChecked = switch.isChecked

                if (isChecked) { // Handle switch on
                    Log.d("Switcher","Switch is ON")

                    addRecipientUpdateEmail(CurrentUser.getId())

                } else { // Handle switch off
                    Log.d("Switcher","Switch is OFF")

                    removeRecipientUpdateEmail(CurrentUser.getId())

                }

                // Save the state of the switch
                val preferences = getSharedPreferences("email_notification_switch", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putBoolean("email_notification", isChecked)
                editor.apply()
            }
            R.id.to_change_password_dialog_btn -> {
                val dialog = ChangePasswordDialogFragment()
                dialog.show(supportFragmentManager, "ChangePasswordDialogFragment")
            }
            R.id.two_fa_layout -> {
                Log.d("MyTag", "Clicked the 2FA Layout")
            }
            R.id.logout_layout -> {
                logout()
                Log.d("Logout", "Clicked the Logout Layout")
            }
            R.id.delete_acc_btn -> {
                val intent = Intent(this@SettingActivity, DeleteMyAccountActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun addRecipientUpdateEmail(idUser: Int) {
        val query = "UPDATE users SET get_updates=1 WHERE id=$idUser"

        try {
            val stmt: Statement = connection!!.createStatement()
            stmt.executeUpdate(query)
            Log.d("EmailUpdates", "ON - Success")
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.d("EmailUpdates", "ON - FAILED")
        }
    }

    private fun removeRecipientUpdateEmail(idUser: Int) {
        val query = "UPDATE users SET get_updates=0 WHERE id=$idUser"

        try {
            val stmt: Statement = connection!!.createStatement()
            stmt.executeUpdate(query)
            Log.d("EmailUpdates", "OFF - Success")
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.d("EmailUpdates", "OFF - FAILED")
        }
    }

    private fun logout() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

}