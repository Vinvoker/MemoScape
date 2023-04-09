package com.example.memoscape

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.DialogFragment.STYLE_NORMAL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

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

                // !!!!!!!!! connect with db
//                val db = MyDatabaseHelper(context)
//                val email = getterEmail

                // Cek if old password correct
//                val sqlGetOldPassword = "SELECT password FROM Users WHERE email='$email'"
//                val real_old_password = db.executeNonQuery(sqlGetOldPassword)
//                if(real_old_password != oldPassword) {
//                    Toast.makeText(context, "Old password wrong", Toast.LENGTH_SHORT).show()
//                }

                // Cek if new password = repeat password
                if (newPassword == repeatPassword) {


//                    val sqlUpdatePassword = "UPDATE users SET password='$newPassword' FROM Users WHERE email='$email' AND password='$oldPassword'"
//                    val success = db.executeNonQuery(sqlUpdatePassword)

                    // FOR NOW ONLY
                    val success = true

                    if(success) {
                        Toast.makeText(context, "Passwords changed successfully", Toast.LENGTH_SHORT).show()

                        // Back to Setting UI
                        dismiss()
                    }

                } else {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }

            }


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

    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_change_password_dialog, container, false)
//    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ChangePasswordDialogFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ChangePasswordDialogFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}