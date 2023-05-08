package com.example.memoscape
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import java.sql.Connection
import java.sql.SQLException


class NoteActivity : AppCompatActivity() {

    private var connection: Connection? = null
    private lateinit var titleTextView: EditText
    private lateinit var contentTextView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_layout)
        connection = DatabaseConnection().createConnection()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val title = intent.getStringExtra("title") ?: "Title"
        val noteContent = intent.getStringExtra("content") ?: "Note Content"

        titleTextView = findViewById<EditText>(R.id.titleTextView)
        contentTextView = findViewById<EditText>(R.id.contentTextView)

        contentTextView.setText(noteContent)
        titleTextView.setText(title)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_attach -> {
                saveNote()
                true
            }
            R.id.action_delete -> {
                saveNote()
                true
            }
            R.id.action_save -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title = titleTextView.text.toString()
        val content = contentTextView.text.toString()
        val id = intent.getIntExtra("id", 0)

        val updateQuery = "UPDATE notes SET title='$title', content='$content' WHERE id=$id"

        try {
            val statement = connection!!.createStatement()
            statement.executeUpdate(updateQuery)
            // Handle any necessary exception handling
        } catch (e: SQLException) {
            e.printStackTrace()
            // Handle the exception
        }

        val resultIntent = Intent()
        resultIntent.putExtra("id", id)
        resultIntent.putExtra("title", title)
        resultIntent.putExtra("content", content)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}