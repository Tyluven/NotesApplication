package com.example.notesapplication.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.example.notesapplication.databinding.FragmentConfirmDeleteNoteBinding
import com.example.notesapplication.databinding.FragmentConfirmDiscardEditNoteBinding

object DialogUtil {
    fun showConfirmDeleteNoteDialog(
        context: Context?,
        btnPositiveCallback: (() -> Unit)? = null,
    ) {
        if (context == null) return

        Dialog(context).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
            FragmentConfirmDeleteNoteBinding.inflate(layoutInflater).run {

                setContentView(this.root)
                btnConfirm.setOnClickListener {
                    btnPositiveCallback?.invoke()
                    dismiss()
                }
                btnCancel.setOnClickListener {
                    dismiss()
                }
            }
        }.show()
    }

    fun showConfirmDiscardNoteDialog(
        context: Context?,
        btnPositiveCallback: (() -> Unit)? = null,
    ) {
        if (context == null) return

        Dialog(context).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
            FragmentConfirmDiscardEditNoteBinding.inflate(layoutInflater).run {
                setContentView(this.root)
                btnConfirm.setOnClickListener {
                    btnPositiveCallback?.invoke()
                    dismiss()
                }
                btnCancel.setOnClickListener {
                    dismiss()
                }
            }
        }.show()
    }
}