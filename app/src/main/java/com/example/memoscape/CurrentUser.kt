package com.example.memoscape

import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

object CurrentUser {

    private var id: Int = 1
    private var email: String = "email@gmail.com"
    private var password: String = "password123"
    private var username: String = "memoscape_user"
    private var get_updates: Boolean = false
    private var notesList: MutableList<Note> = mutableListOf()


    // Setter

    fun setUser(id: Int, email: String, password: String, username: String, get_updates: Boolean) {
        this.id = id
        this.email = email
        this.password = password
        this.username = username
        this.get_updates = get_updates
    }

    fun getId(): Int {
        return this.id
    }

    fun getUsername(): String {
        return this.username
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
        notesList = mutableListOf()
        getNotes()
        return notesList
    }

}