package com.example.memoscape

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memoscape.R

class MainActivity : AppCompatActivity() {

    // Initialize list of notes
    private val notesList = mutableListOf<Note>()

    data class Note(val title: String, val content: String)

    class NotesAdapter(private val notesList: MutableList<Note>) :
        RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

        private var listener: OnItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.notes_list_item, parent, false)
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
            val titleTextView: TextView = itemView.findViewById(R.id.note_title)
            val contentTextView: TextView = itemView.findViewById(R.id.note_content)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        // Buat cek UI Activity lain ygyyyy
        val intent = Intent(this@MainActivity, LoginActivity::class.java) // ubah ke nama file Activity masing"
        startActivity(intent)

//        // Create some sample notes
//        val note1 = Note("Note 1", "This is the content of note 1")
//        val note2 = Note("Note 2", "This is the content of note 2")
//        val note3 = Note("Note 3", "This is the content of note 3")
//
//        // Add the notes to the list
//        notesList.add(note1)
//        notesList.add(note2)
//        notesList.add(note3)

//        // Set up the RecyclerView
//        val notesRecyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
//        notesRecyclerView.layoutManager = LinearLayoutManager(this)
//        val adapter = NotesAdapter(notesList)
//        notesRecyclerView.adapter = adapter
//
//        // Set up item click listener
//        adapter.setOnItemClickListener(object : NotesAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                val note = notesList[position]
//                showNoteDialog(note)
//            }
//        })
 }
//
//    // Show dialog with note details
//    private fun showNoteDialog(note: Note) {
//        val dialog = Dialog(this)
//        dialog.setContentView(R.layout.note_layout)
//        dialog.findViewById<TextView>(R.id.note_title).text = note.title
//        dialog.findViewById<TextView>(R.id.note_content).text = note.content
//        dialog.show()
//    }
}
