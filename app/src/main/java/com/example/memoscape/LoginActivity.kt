package com.example.memoscape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.memoscape.CurrentUser.setUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var dbConnection: DatabaseConnection

    private lateinit var emailInput : EditText
    private lateinit var passInput : EditText
    private lateinit var login : Button

    //Google
    private lateinit var gLogin : Button
    private lateinit var client: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.input_login_email)
        passInput = findViewById(R.id.input_login_password)
        login = findViewById(R.id.login_button)

        login.setOnClickListener(this)

        //google
        gLogin = findViewById(R.id.google_auth)
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(this, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 10001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {
                        val i = Intent(this, HomeActivity::class.java)
                        startActivity(i)

                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
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

    private fun findUser(email: String, password: String) : Boolean{
        dbConnection = DatabaseConnection()
        val connection = dbConnection.createConnection()
        val query = "SELECT * FROM users WHERE email = $email AND password = $password "

        lateinit var curUser : CurrentUser

        try {
            val stmt: Statement = connection!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                curUser.setUser (
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("photo_url"),
                    rs.getBoolean("get_updates")
                )
            }

        } catch (e:SQLException) {
            e.printStackTrace()
            return false
        }

        return true
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_button -> {
                val email = emailInput.text.toString().trim()
                val password = passInput.text.toString().trim()

                var isEmptyFields = false
                if (email.isEmpty()) {
                    isEmptyFields = true
                    emailInput.error = "Field ini tidak boleh kosong"
                }
                if (password.isEmpty()) {
                    isEmptyFields = true
                    passInput.error = "Field ini tidak boleh kosong"
                }

                if (!isEmptyFields) {
                    if (findUser(email, password)) {
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                }
            }
            R.id.google_auth -> {
                val intent = client.signInIntent
                startActivityForResult(intent, 10001)

            }
            R.id.signup_hyperlink -> {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
    }
    }

}