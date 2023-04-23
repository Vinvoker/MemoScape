package com.example.memoscape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.sql.SQLException
import java.sql.Statement

class DeleteMyAccountActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var confirmDeleteBtn: Button
    private lateinit var deleteConfirmEdt: EditText

    private val dbConnection = DatabaseConnection()
    private val connection = dbConnection.createConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_my_account)

        supportActionBar?.hide()

        confirmDeleteBtn = findViewById(R.id.confirm_delete_acc_btn)
        confirmDeleteBtn.setOnClickListener(this)

        deleteConfirmEdt = findViewById(R.id.delete_confirm_edt)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.confirm_delete_acc_btn -> {

                val deleteConfirmText = deleteConfirmEdt.text.toString()
                val delete = resources.getString(R.string.delete)

                if(deleteConfirmText.isEmpty()) {
                    deleteConfirmEdt.error = resources.getString(R.string.account_deletion_confirm)

                } else {

                    val isTextSame = deleteConfirmText.equals(delete, ignoreCase = false)

                    if (isTextSame) {

                        val success = deleteAccount(CurrentUser.getId())

                        if (success) {
                            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT)
                                .show()

                            // Back to Login Activity
                            val intent = Intent(this@DeleteMyAccountActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "Account deleted FAILED", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } else {
                        Toast.makeText(this, "Text does not match with required word", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
    }

    private fun deleteAccount(idUser: Int): Boolean {
        val query = "Delete FROM users WHERE id=$idUser"

        return try {
            val stmt: Statement = connection!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

}