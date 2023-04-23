package com.example.memoscape

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

/**
 * A simple [Fragment] subclass.
 * Use the [ChangePasswordDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePasswordDialogFragment : DialogFragment() {

    private lateinit var oldPasswordEdt: EditText
    private lateinit var newPasswordEdt: EditText
    private lateinit var repeatPasswordEdt: EditText
    private lateinit var changePasswordBtn: Button

    private val dbConnection = DatabaseConnection()
    private val connection = dbConnection.createConnection()

    interface OnPasswordChangedListener {
        fun onPasswordChanged(newPassword: String)
    }

    private lateinit var listener: OnPasswordChangedListener

    fun setOnPasswordChangedListener(listener: OnPasswordChangedListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_change_password_dialog, null)
        initView(view)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
            .setTitle(R.string.change_password)
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
        return builder.create().apply {
            setCanceledOnTouchOutside(false)
            setOnShowListener {
                getButton(DialogInterface.BUTTON_POSITIVE).visibility = View.GONE
            }
        }
    }

    private fun initView(view: View) {
        oldPasswordEdt = view.findViewById(R.id.old_password_edt)
        newPasswordEdt = view.findViewById(R.id.new_password_edt)
        repeatPasswordEdt = view.findViewById(R.id.repeat_password_edt)
        changePasswordBtn = view.findViewById(R.id.btn_change_password)

        changePasswordBtn.setOnClickListener {
            val oldPassword = oldPasswordEdt.text.toString()
            val newPassword = newPasswordEdt.text.toString()
            val repeatPassword = repeatPasswordEdt.text.toString()

            var isEmptyFields = false

            // Cek apakah ada field yang kosong
            if(oldPassword.isEmpty()) {
                isEmptyFields = true
                oldPasswordEdt.error = "This field cannot be empty"
            }

            if(newPassword.isEmpty()) {
                isEmptyFields = true
                newPasswordEdt.error = "This field cannot be empty"
            }

            if(repeatPassword.isEmpty()) {
                isEmptyFields = true
                repeatPasswordEdt.error = "This field cannot be empty"
            }

            // Jika semua field sudah diisi
            if(!isEmptyFields) {

                // Check if old password correct
                val realOldPassword = getOldPassword(1)

                if(realOldPassword != oldPassword) {
                    Toast.makeText(context, "Old password wrong", Toast.LENGTH_SHORT).show()

                } else {

                    // Check if new password = repeat password
                    if (newPassword == repeatPassword) {

                        // Check if old and new password same or not
                        if(oldPassword != newPassword) {

                            val success = changePassword(1, newPassword)

                            if (success) {
                                Toast.makeText(
                                    context,
                                    "Passwords changed successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Back to Setting UI
                                dismiss()

                            } else {
                                Toast.makeText(
                                    context,
                                    "Passwords changed FAILED",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else { // oldPassword == newPassword
                            Toast.makeText(context, "Old and new password same", Toast.LENGTH_SHORT).show()
                        }

                    } else { // newPassword != repeatPassword
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getOldPassword(idUser: Int): String {
        val query = "SELECT password FROM users WHERE id=$idUser"
        var olPassword = ""
        try {
            val stmt: Statement = connection!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                olPassword = rs.getString("password")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return ""
        }
        Log.d("Old_Pass", olPassword)
        return olPassword
    }

    private fun changePassword(idUser: Int, newPassword: String): Boolean {

        val query = "UPDATE users SET password='$newPassword' WHERE id=$idUser"

        return try {
            val stmt: Statement = connection!!.createStatement()
            stmt.executeUpdate(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnPasswordChangedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement ChangePasswordDialogListener")
        }
    }

//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

}