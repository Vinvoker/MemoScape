package com.example.memoscape

import android.os.Parcelable

object CurrentUser {

    private var id: Int = 9
    private var email: String = "email@gmail.com"
    private var password: String = "password123"
    private var username: String = "memoscape_user"
    private var photo_url: String = "https://upload.wikimedia.org/wikipedia/en/a/a9/MarioNSMBUDeluxe.png"
    private var get_updates: Boolean = false

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

}