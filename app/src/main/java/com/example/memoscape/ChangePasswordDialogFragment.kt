package com.example.memoscape

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
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

    private lateinit var listener: ChangePasswordDialogListener

    private lateinit var oldPasswordEdt: EditText
    private lateinit var newPasswordEdt: EditText
    private lateinit var repeatPasswordEdt: EditText
    private lateinit var changePasswordBtn: Button

    internal interface ChangePasswordDialogListener {
        fun onChangePasswordDialogPositiveClick(oldPassword: String, newPassword: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_change_password_dialog, null)
        initView(view)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
            .setTitle(R.string.change_password)
        return builder.create()
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
            if (newPassword == repeatPassword) {
                listener?.onChangePasswordDialogPositiveClick(oldPassword, newPassword)
                dismiss()
            } else {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as ChangePasswordDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement ChangePasswordDialogListener")
        }
    }

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