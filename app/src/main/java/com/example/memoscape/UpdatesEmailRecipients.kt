package com.example.memoscape

class UpdatesEmailRecipients(
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

}