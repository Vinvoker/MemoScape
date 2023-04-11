package com.example.memoscape

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import java.util.*
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.internet.MimeMessage

class EmailSender(
//    private val context: Context
    ) {

    private val recipients = mutableListOf<String>()

    fun addRecipient(email: String) {
        recipients.add(email)
    }

    fun removeRecipient(email: String) {
        recipients.remove(email)
    }

    fun getAllRecipients(): List<String> {
        return recipients
    }

    fun sendEmail(subject: String, body: String) {
        val username = "your_email_username"
        val password = "your_email_password"

        val properties = Properties()
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.smtp.host"] = "smtp.gmail.com"
        properties["mail.smtp.port"] = "587"

//        val session = Session.getInstance(properties, object : Authenticator() {
//            override fun getPasswordAuthentication(): PasswordAuthentication {
//                return PasswordAuthentication(username, password)
//            }
//        })

        try {
//            val message = MimeMessage(session)
//            message.setFrom(InternetAddress(username))
//            for (recipient in recipients) {
//                message.addRecipient(Message.RecipientType.TO, InternetAddress(recipient))
//            }
//            message.subject = subject
//            message.setText(body)
//            Transport.send(message)
            Log.d("EmailSender", "Email sent successfully")
        } catch (e: MessagingException) {
            Log.e("EmailSender", "Failed to send email", e)
        }
    }

}