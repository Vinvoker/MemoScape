package com.example.memoscape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class DeleteMyAccountActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var confirmDeleteBtn: Button
    private lateinit var deleteConfirmEdt: EditText

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

                        // !!!!!!!!! connect with db
//                        val db = MyDatabaseHelper(context)
//                        val email = getterEmail
//
//                        val sqlDeleteAccount = "Delete FROM users WHERE email='$email'"
//                        val success = db.executeNonQuery(sqlDeleteAccount)

                        // FOR NOW ONLY
                        val success = true

                        if (success) {
                            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT)
                                .show()

                            // Back to Login Activity
                        val intent = Intent(this@DeleteMyAccountActivity, LoginActivity::class.java)
                        startActivity(intent)

                        } else {
                            Toast.makeText(this, "Something went wrong with sqlDelAcc", Toast.LENGTH_SHORT)
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


}