package com.example.memoscape
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText


class NoteActivity : AppCompatActivity() {

    private lateinit var titleTextView: EditText
    private lateinit var contentTextView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        // Nanti ambil data dari intent
//        val title = intent.getStringExtra(title) ?: ""
//        val content = intent.getStringExtra(content) ?: ""

        val titleTextView = findViewById<EditText>(R.id.titleTextView)
        val contentTextView = findViewById<EditText>(R.id.contentTextView)

        contentTextView.setText("Dummy Text")
        titleTextView.setText("Note Title")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.saveNote -> {
//                saveNote()
//                true
//            }
//            android.R.id.home -> {
//                onBackPressed()
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title = titleTextView.text.toString()
        val content = contentTextView.text.toString()

        val intent = Intent().apply {
            putExtra("title", title)
            putExtra("content", content)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}