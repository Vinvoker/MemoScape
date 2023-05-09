package com.example.memoscape
import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
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

    private fun getStyledContent(content: CharSequence): String {
        val spannable = SpannableStringBuilder(content)
        val spans = spannable.getSpans(0, spannable.length, StyleSpan::class.java)
        for (span in spans) {
            val start = spannable.getSpanStart(span)
            val end = spannable.getSpanEnd(span)

            Log.d("Span", "Start: $start, End: $end")
        }

        val styledContent = StringBuilder()
        var currentIndex = 0

        for (i in spans.indices) {
            val start = spannable.getSpanStart(spans[i])
            val end = spannable.getSpanEnd(spans[i])

            if (currentIndex < start) {
                // Append the plain text before the styled section
                styledContent.append(content.subSequence(currentIndex, start))
            }

            // Append the styled section with the <b> tags
            val styledText = content.subSequence(start, end)
            styledContent.append("<b>$styledText</b>")

            currentIndex = end
            Log.d("styledcontentloop", styledContent.toString())
        }

        if (currentIndex < content.length) {
            // Append the remaining plain text after the last styled section
            styledContent.append(content.subSequence(currentIndex, content.length))
        }
        Log.d("styledcontent", styledContent.toString())

        // Convert the styled content to HTML format
        val styledContentHtml = HtmlCompat.toHtml(SpannableStringBuilder.valueOf(styledContent.toString()), HtmlCompat.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)

        return styledContentHtml
    }

    private fun saveNote() {
        val title = titleTextView.text.toString()
        val content = contentTextView.text.toString()
        val styledContent = getStyledContent(content)
        Log.d("styledcontent", styledContent)
        val id = intent.getIntExtra("id", 0)
        val userId = CurrentUser.getId()

        if (id == 0) {
            // Insert a new note
            val insertQuery = "INSERT INTO notes (user_id, title, content) VALUES ('$userId', '$title', '$styledContent')"

            try {
                val statement = connection!!.createStatement()
                statement.executeUpdate(insertQuery)
                // Handle any necessary exception handling
            } catch (e: SQLException) {
                e.printStackTrace()
                // Handle the exception
            }
        } else {
            val updateQuery = "UPDATE notes SET title='$title', content='$styledContent' WHERE id=$id"

            try {
                val statement = connection!!.createStatement()
                statement.executeUpdate(updateQuery)
                // Handle any necessary exception handling
            } catch (e: SQLException) {
                e.printStackTrace()
                // Handle the exception
            }
        }

        val resultIntent = Intent()
        resultIntent.putExtra("id", id)
        resultIntent.putExtra("title", title)
        resultIntent.putExtra("content", styledContent)
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
            // Handle the exception
        }
    }
}