package com.example.memoscape

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var notesListView: ListView
    private lateinit var notesList: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        titleTextView = findViewById(R.id.title_textview)
        notesListView = findViewById(R.id.notes_listview)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.newnote -> {
                    startActivity(Intent(this, NoteActivity::class.java))
                    true
                }
                R.id.settings -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }
                else -> false
            }
        }

        notesList = mutableListOf(
            Note("Note 1", "This is the first note."),
            Note("Note 2", "This is the second note."),
            Note("Note 3", "This is the third note.")
        )

        val adapter = NotesAdapter(this, notesList)
        notesListView.adapter = adapter

        notesListView.setOnItemClickListener { _, _, position, _ ->
            val note = notesList[position]

            // Launch the NoteActivity and pass the selected note as an extra
            val intent = Intent(this, NoteActivity::class.java).apply {
                putExtra("title", note.title)
                putExtra("content", note.content)
            }
            startActivity(intent)
        }
    }

    data class Note(val title: String, val content: String)

    class NotesAdapter(private val context: Context, private val notesList: MutableList<Note>) :
        ArrayAdapter<Note>(context, R.layout.notes_list_item, notesList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(
                R.layout.notes_list_item, parent, false
            )

            val note = notesList[position]

            val titleTextView = view.findViewById<TextView>(R.id.note_title)
            val contentTextView = view.findViewById<TextView>(R.id.note_content)

            titleTextView.text = note.title
            contentTextView.text = note.content

            return view
        }
    }
}
