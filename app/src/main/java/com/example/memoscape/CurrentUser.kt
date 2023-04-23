package com.example.memoscape

object CurrentUser {

    private var id: Int = 9
    private var email: String = "email@gmail.com"
    private var password: String = "password123"
    private var username: String = "memoscape_user"
    private var photo_url: String = "https://"
    private var get_updates: Boolean = false

    // Setter

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