package com.example.memoscape

import android.app.Activity
import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memoscape.MainActivity
import com.example.memoscape.SettingActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var titleTextView: EditText
    private lateinit var notesListView: ListView
    private lateinit var notesList: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.memoscape.R.layout.activity_home)

        // Find the UI elements by their IDs
        val titleTextView: TextView = findViewById(com.example.memoscape.R.id.title_textview)
        val settingsImage: ImageView = findViewById(com.example.memoscape.R.id.settings_image)
        notesListView = findViewById(com.example.memoscape.R.id.notes_listview)
        val newNoteButton: Button = findViewById(com.example.memoscape.R.id.new_note_button)

        settingsImage.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        newNoteButton.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }

        // Initialize the notes list
        notesList = mutableListOf(
            Note("Note 1", "This is the first note."),
            Note("Note 2", "This is the second note."),
            Note("Note 3", "This is the third note.")
        )

        // Set up the ListView
        val adapter = NotesAdapter(this, notesList)
        notesListView.adapter = adapter

        val title = titleTextView.text.toString()
        //val content = contentTextView.text.toString()

        // Set up item click listener
        notesListView.setOnItemClickListener { _, _, position, _ ->
            val note = notesList[position]

            // Launch the NoteActivity and pass the selected note as an extra
            val intent = Intent(this, NoteActivity::class.java).apply {
                putExtra("title", title)
                //putExtra("content", content)
            }
            startActivity(intent)
        }
    }

    data class Note(val title: String, val content: String)

    class NotesAdapter(private val context: Context, private val notesList: MutableList<Note>) :
        ArrayAdapter<Note>(context, com.example.memoscape.R.layout.notes_list_item, notesList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(
                com.example.memoscape.R.layout.notes_list_item, parent, false
            )

            val note = notesList[position]

            val titleTextView = view.findViewById<TextView>(com.example.memoscape.R.id.note_title)
            val contentTextView = view.findViewById<TextView>(com.example.memoscape.R.id.note_content)

            titleTextView.text = note.title
            contentTextView.text = note.content

            return view
        }
    }

    private fun showNoteDialog(note: Note) {
        // Show a dialog with the note details
    }

    private fun showNewNoteOptionsDialog() {
        // Show a dialog to choose between creating a new text note or attaching an image
    }
}
