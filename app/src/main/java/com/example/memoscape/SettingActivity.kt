package com.example.memoscape

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity(), View.OnClickListener, ChangePasswordDialogFragment.OnPasswordChangedListener {

//    private lateinit var toolbar: Toolbar
    private lateinit var emailUpdateSwitch: SwitchCompat
    private lateinit var changePasswordToDialogBtn: Button
    private lateinit var twoFAlayout: LinearLayout
    private lateinit var radioGroupTheme: RadioGroup
    private lateinit var deleteMyAccount: Button

    private val sharedPref by lazy {
        getSharedPreferences("email_notification_switch", MODE_PRIVATE)
    }

//    private lateinit var autoCompleteTV: AutoCompleteTextView
//    private lateinit var adapterItems: ArrayAdapter<String>
//
//    val item = arrayOf("Student", "Teacher", "Personal")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

//        supportActionBar?.setTitle(resources.getString(R.string.notification_and_setting_title))

        supportActionBar?.apply {
            setTitle(resources.getString(R.string.notification_and_setting_title))
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@SettingActivity, R.color.light_grey)))
        }

//        toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        emailUpdateSwitch = findViewById(R.id.email_notification_switch)
        emailUpdateSwitch.isChecked = sharedPref.getBoolean("email_update_switch_state", false)
        emailUpdateSwitch.setOnClickListener(this)

        changePasswordToDialogBtn = findViewById(R.id.to_change_password_dialog_btn)
        changePasswordToDialogBtn.setOnClickListener(this)

        twoFAlayout = findViewById(R.id.two_fa_layout)
        twoFAlayout.setOnClickListener(this)

        deleteMyAccount = findViewById(R.id.delete_acc_btn)
        deleteMyAccount.setOnClickListener(this)

        // about THEME Radio Group
        radioGroupTheme = findViewById(R.id.radio_group_theme)
        radioGroupTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_light_system_setting -> {
                    Log.d("Theme", "Clicked SYSTEM SETTING Theme")

//                    lifecycleScope.launch(Dispatchers.Default) {
//                        // Perform long-running operation here
//                        runOnUiThread {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
//                        }
//                    }
                }
                R.id.radio_light_theme -> {
                    Log.d("Theme", "Clicked LIGHT Theme")

//                    lifecycleScope.launch(Dispatchers.Default) {
//                        // Perform long-running operation here
//                        runOnUiThread {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
//                        }
//                    }
                }
                R.id.radio_dark_theme -> {
                    Log.d("Theme", "Clicked DARK Theme")

//                    lifecycleScope.launch(Dispatchers.Default) {
//                        // Perform long-running operation here
//                        runOnUiThread {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
//                        }
//                    }
                }
            }
        }


//        autoCompleteTV = findViewById(R.id.auto_complete_txt)
//        adapterItems = ArrayAdapter(this@SettingActivity, R.layout.memoscape_for_list_item, item)
//        autoCompleteTV.setAdapter(adapterItems)
//        autoCompleteTV.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
//            val selectedItem = adapterView.getItemAtPosition(i).toString()
//            Toast.makeText(this, "Item: $selectedItem", Toast.LENGTH_SHORT).show()
//        }

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

                val emailSender = EmailSender()

                // Perform necessary actions based on the switch state
                if (isChecked) {
                    // Handle switch on
                    Log.d("Switcher","Switch is ON")

                    // The real code
//                    recipients.add(User.email)
                    emailSender.addRecipient("recipient1@example.com")

                    // Pengecekan
                    val recipients =  emailSender.getAllRecipients().toString()
                    Log.d("Recipients +", recipients)

                } else {
                    // Handle switch off
                    Log.d("Switcher","Switch is OFF")

                    // The real code
//                    recipients.remove(User.email)
                    emailSender.removeRecipient("recipient1@example.com")

                    // Pengecekan
                    val recipients =  emailSender.getAllRecipients().toString()
                    Log.d("Recipients +", recipients)

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
            R.id.delete_acc_btn -> {
                val intent = Intent(this@SettingActivity, DeleteMyAccountActivity::class.java)
                startActivity(intent)
            }
        }


    }

    override fun onPasswordChanged(newPassword: String) {
        TODO("Not yet implemented")
    }

}