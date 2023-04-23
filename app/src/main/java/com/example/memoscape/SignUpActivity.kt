package com.example.memoscape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText

class SignUpActivity : AppCompatActivity(), OnClickListener {
    private lateinit var dbConnection: DatabaseConnection

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputPassword2: EditText
    private lateinit var inputUsername: EditText
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        inputEmail = findViewById(R.id.input_email)
        inputPassword = findViewById(R.id.input_password)
        inputPassword2 = findViewById(R.id.input_password2)
        inputUsername = findViewById(R.id.input_username)
        btnSignUp = findViewById(R.id.btn_sign_up)

        btnSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_sign_up) {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()
            val password2 = inputPassword2.text.toString().trim()
            val username = inputUsername.text.toString().trim()

            var isEmptyFields = false
            if (email.isEmpty()) {
                isEmptyFields = true
                inputEmail.error = "Field ini tidak boleh kosong"
            }
            if (password.isEmpty()) {
                isEmptyFields = true
                inputPassword.error = "Field ini tidak boleh kosong"
            }
            if (password2.isEmpty()) {
                isEmptyFields = true
                inputPassword2.error = "Field ini tidak boleh kosong"
            }
            if (username.isEmpty()) {
                isEmptyFields = true
                inputUsername.error = "Field ini tidak boleh kosong"
            }

            if (!isEmptyFields) {
                if (password != password2) {
                    inputPassword2.error = "Password yang diberikan salah"
                }else{
                    createUser(email, password, username)
                }
            }
        }
    }
    private fun createUser(email: String, password: String, username: String) {
        dbConnection = DatabaseConnection()
        val connection = dbConnection.createConnection()
        val query = "INSERT INTO users (email, password, username) VALUES (?, ?, ?)"
        val preparedStatement = connection?.prepareStatement(query)

        preparedStatement?.apply {
            setString(1, email)
            setString(2, password)
            setString(3, username)
            executeUpdate()
            close()

        }
    }
}