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
import androidx.recyclerview.widget.RecyclerView
import com.example.memoscape.MainActivity
import com.example.memoscape.SettingActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var notesListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.memoscape.R.layout.activity_home)

        // Find the UI elements by their IDs
        val titleTextView: TextView = findViewById(com.example.memoscape.R.id.title_textview)
        val settingsButton: Button = findViewById(com.example.memoscape.R.id.settings_button)
        notesListView = findViewById(com.example.memoscape.R.id.notes_listview)
        val newNoteButton: Button = findViewById(com.example.memoscape.R.id.new_note_button)

        // Set the click listener for the settings button
        settingsButton.setOnClickListener {
            // Start the settings activity
            startActivity(Intent(this, SettingActivity::class.java))
        }

        // Set the click listener for the new note button
        newNoteButton.setOnClickListener {
            // Show the new note options dialog
            showNewNoteOptionsDialog()
        }

        // Populate the notes list with some sample data
        val notes = listOf(
            Note("Note 1", "This is a preview of Note 1"),
            Note("Note 2", "This is a preview of Note 2"),
            Note("Note 3", "This is a preview of Note 3")
        )
        // val adapter = NotesAdapter(this, notes)
        // notesListView.adapter = adapter

        // Set the click listener for the notes list items
        notesListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                // Start the note activity for the selected note
            }
    }

    data class Note(val title: String, val preview: String)

    class NotesAdapter(private val context: Context, private val notesList: MutableList<MainActivity.Note>) :
        RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

        private var listener: OnItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
            val itemView = LayoutInflater.from(context)
                .inflate(com.example.memoscape.R.layout.notes_list_item, parent, false)
            return NotesViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
            val currentItem = notesList[position]
            holder.titleTextView.text = currentItem.title
            holder.contentTextView.text = currentItem.content
        }

        override fun getItemCount(): Int {
            return notesList.size
        }

        inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
            val titleTextView: TextView = itemView.findViewById(com.example.memoscape.R.id.note_title)
            val contentTextView: TextView = itemView.findViewById(com.example.memoscape.R.id.note_content)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }

        interface OnItemClickListener {
            fun onItemClick(position: Int)
        }

        fun setOnItemClickListener(listener: OnItemClickListener) {
            this.listener = listener
        }
    }

    private fun showNewNoteOptionsDialog() {
        // Show a dialog to choose between creating a new text note or attaching an image
    }
}
