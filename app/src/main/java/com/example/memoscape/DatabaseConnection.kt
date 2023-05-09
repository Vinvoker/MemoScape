package com.example.memoscape

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseConnection{

    fun createConnection(): Connection? {
        val url = "jdbc:mysql://10.0.2.2:4306/memoscape"
        val user = "root"
        val password = ""

        return try {
//            Class.forName("com.mysql.cj.jdbc.Driver")
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            DriverManager.getConnection(url, user, password)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

}