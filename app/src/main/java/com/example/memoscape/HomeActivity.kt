package com.example.memoscape

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
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var notesList: MutableList<CurrentUser.Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        titleTextView = findViewById(R.id.title_textview)
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

        val userId = CurrentUser.getId()
        notesList = getNotesByUserId(userId)

        val notesRecyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesAdapter(this, notesList)
        notesRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : NotesAdapter.OnItemClickListener {
            override fun onItemClick(note: CurrentUser.Note) {
                val intent = Intent(this@HomeActivity, NoteActivity::class.java).apply {
                    putExtra("title", note.title)
                    putExtra("content", note.content)
                }
                startActivity(intent)
            }
        })
    }

    data class Note(val title: String, val content: String)

    class NotesAdapter(private val context: Context, private val notesList: List<CurrentUser.Note>) :
        RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

        private var itemClickListener: OnItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.notes_list_item, parent, false)
            return NoteViewHolder(view)
        }
        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            val note = notesList[position]
            holder.bind(note)

            holder.itemView.setOnClickListener {
                itemClickListener?.onItemClick(note)
            }
        }

        override fun getItemCount(): Int {
            return notesList.size
        }

        fun setOnItemClickListener(listener: OnItemClickListener) {
            itemClickListener = listener
        }

        interface OnItemClickListener {
            fun onItemClick(note: CurrentUser.Note)
        }

        inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val titleTextView: TextView = itemView.findViewById(R.id.note_title)
            private val contentTextView: TextView = itemView.findViewById(R.id.note_content)

            fun bind(note: CurrentUser.Note) {
                titleTextView.text = note.title
                contentTextView.text = note.content
            }
        }
    }

    private fun getNotesByUserId(userId: Int): MutableList<CurrentUser.Note> {
        val notesList: MutableList<CurrentUser.Note> = mutableListOf()
        val currentUserNotes = CurrentUser.getNotesList()

        for (note in currentUserNotes) {
            notesList.add(note)
        }

        return notesList
    }
}