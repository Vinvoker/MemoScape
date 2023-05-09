package com.example.memoscape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
import kotlin.math.sign

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
        supportActionBar?.hide()

        //Manual Login
        emailInput = findViewById(R.id.input_login_email)
        passInput = findViewById(R.id.input_login_password)
        login = findViewById(R.id.login_button)

        login.setOnClickListener {
            val inputEmail: String = emailInput.text.toString()
            val inputPassword: String = passInput.text.toString()

            val successLogin: Boolean = findUser(inputEmail, inputPassword)

            if (successLogin){
                Toast.makeText(applicationContext, "Welcome!", Toast.LENGTH_SHORT).show()
                navigateToHomeScreen()
            } else {
                Toast.makeText(applicationContext, "No Account Found!", Toast.LENGTH_SHORT).show()
            }
        }

        //clickable text view
        signupTextSpan()

        //google
        gLogin = findViewById(R.id.google_auth)
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(this, options)
        gLogin.setOnClickListener{
            val intent = client.signInIntent
            startActivityForResult(intent, 10001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 10001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            val activeEmail = account.email.toString()
            val activeUsername = account.displayName.toString()

            val accountExist : Boolean = checkAccount(activeEmail)

            if (!accountExist) {
                createUser(activeEmail, "default", activeUsername)
            }

            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {
                        val i = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(i)

                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun checkAccount (email: String) : Boolean {
        dbConnection = DatabaseConnection()
        val connection = dbConnection.createConnection()
        val query = "SELECT * FROM users WHERE email = '$email'"

        try {
            val stmt: Statement = connection!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            if (rs.first() == false) { // No result ; Email tidak ada di database
                return false
            }

        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return true
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

    private fun findUser(email: String, password: String) : Boolean {
        dbConnection = DatabaseConnection()
        val connection = dbConnection.createConnection()

        val curUser = CurrentUser

        val query = "SELECT * FROM users WHERE email = '$email' AND password = '$password' "

        try {
            val stmt: Statement = connection!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            if (rs.next()) {
                curUser.setUser(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("username"),
                    rs.getBoolean("get_updates")
                )
            } else {
                // No matching user found
                return false
            }

        } catch (e:SQLException) {
            e.printStackTrace()
            return false
        }

        return true
    }

    private fun signupTextSpan() {
        val signUpLink: TextView = findViewById(R.id.signup_hyperlink)

        signUpLink.setOnClickListener{
            val i = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    private fun navigateToHomeScreen(){
        val i = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(i)
    }

    override fun onClick(v: View?) {

    }


}