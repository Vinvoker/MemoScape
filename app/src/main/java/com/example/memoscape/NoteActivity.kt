package com.example.memoscape
import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
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

        val spannedText = HtmlCompat.fromHtml(noteContent, HtmlCompat.FROM_HTML_MODE_LEGACY)
        contentTextView.setText(spannedText)
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
            R.id.action_bold -> {
                boldText(contentTextView)
                true
            }
            R.id.action_italic -> {
                italicText(contentTextView)
                true
            }
            R.id.action_underline -> {
                underlineText(contentTextView)
                true
            }
            R.id.action_delete -> {
                deleteNote()
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
        val resultIntent = Intent()
        val title = titleTextView.text.toString()
        val content = contentTextView.text.toString()
        val id = intent.getIntExtra("id", 0)
        val userId = CurrentUser.getId()

        if (id == 0) {
            val insertQuery = "INSERT INTO notes (user_id, title, content) VALUES ('$userId', '$title', '$content')"
            try {
                val statement = connection!!.createStatement()
                statement.executeUpdate(insertQuery)

                val selectLastInsertIdQuery = "SELECT LAST_INSERT_ID() as newId"
                val result = statement.executeQuery(selectLastInsertIdQuery)

                if (result.next()) {
                    val newNoteId = result.getInt("newId")
                    resultIntent.putExtra("newId", newNoteId)
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        } else {
            val updateQuery = "UPDATE notes SET title='$title', content='$content' WHERE id=$id"
            resultIntent.putExtra("id", id)

            try {
                val statement = connection!!.createStatement()
                statement.executeUpdate(updateQuery)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        resultIntent.putExtra("title", title)
        resultIntent.putExtra("content", content)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun boldText(contentTextView: TextView) {
        val selectedText = contentTextView.text?.subSequence(contentTextView.selectionStart, contentTextView.selectionEnd)
        if (!selectedText.isNullOrBlank() && selectedText != titleTextView) {
            val htmlString = "<b>${selectedText}</b>"
            val spannedText: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlString)
            }
            val spannable = SpannableString(spannedText)
            spannable.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val editable = contentTextView.text as Editable
            editable.replace(contentTextView.selectionStart, contentTextView.selectionEnd, spannable)
        }
    }

    private fun italicText(contentTextView: TextView) {
        val selectedText = contentTextView.text?.subSequence(contentTextView.selectionStart, contentTextView.selectionEnd)
        if (!selectedText.isNullOrBlank() && selectedText != titleTextView) {
            val htmlString = "<i>${selectedText}</i>"
            val spannedText: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlString)
            }
            val spannable = SpannableString(spannedText)
            spannable.setSpan(StyleSpan(Typeface.ITALIC), 0, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val editable = contentTextView.text as Editable
            editable.replace(contentTextView.selectionStart, contentTextView.selectionEnd, spannable)
        }
    }

    private fun underlineText(contentTextView: TextView) {
        val selectedText = contentTextView.text?.subSequence(contentTextView.selectionStart, contentTextView.selectionEnd)
        if (!selectedText.isNullOrBlank() && selectedText != titleTextView) {
            val htmlString = "<u>${selectedText}</u>"
            val spannedText: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlString)
            }
            val spannable = SpannableString(spannedText)
            spannable.setSpan(UnderlineSpan(), 0, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val editable = contentTextView.text as Editable
            editable.replace(contentTextView.selectionStart, contentTextView.selectionEnd, spannable)
        }
    }


    private fun deleteNote() {
        val id = intent.getIntExtra("id", 0)
        val deleteQuery = "DELETE FROM notes WHERE id=$id"

        try {
            val statement = connection!!.createStatement()
            statement.executeUpdate(deleteQuery)
            // Handle any necessary exception handling

            val resultIntent = Intent()
            resultIntent.putExtra("id", id)
            resultIntent.putExtra("isDeleted", true)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}