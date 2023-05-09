package com.example.memoscape

import android.os.Parcelable

import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

object CurrentUser {

    private var id: Int = 1
    private var email: String = "email@gmail.com"
    private var password: String = "password123"
    private var username: String = "memoscape_user"
    private var photo_url: String = "https://upload.wikimedia.org/wikipedia/en/a/a9/MarioNSMBUDeluxe.png"
    private var get_updates: Boolean = false
    private var notesList: MutableList<Note> = mutableListOf()


    // Setter

    fun setUser(id: Int, email: String, password: String, username: String, photo_url: String, get_updates: Boolean) {
        this.id = id
        this.email = email
        this.password = password
        this.username = username
        this.photo_url = photo_url
        this.get_updates = get_updates
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPhotoUrl(photo_url: String) {
        this.photo_url = photo_url
    }

    fun setGetUpdates(get_updates: Boolean) {
        this.get_updates = get_updates
    }

    // Getter

    fun getId(): Int {
        return this.id
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }

    fun getUsername(): String {
        return this.username
    }

    fun getPhotoUrl(): String {
        return this.photo_url
    }

    fun getGetUpdates(): Boolean {
        return this.get_updates
    }

    data class Note(val id: Int, var title: String, var content: String)

    fun getNotes() {
        val connection = DatabaseConnection().createConnection()
        val query = "SELECT id, title, content FROM notes WHERE user_id= $id"
        try {
            val stmt: Statement = connection!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                val id = rs.getInt("id")
                val title = rs.getString("title")
                val content = rs.getString("content")
                val note = Note(id, title, content)
                notesList.add(note)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    init {
        getNotes()
    }

    fun getNotesList(): MutableList<Note> {
        return notesList
    }

}