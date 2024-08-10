package com.example.notesapplication.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.notesapplication.databinding.LayoutLoadingBinding

open class BaseFragment : Fragment() {
    private var loadingDialog: Dialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (loadingDialog == null) {
            loadingDialog = context?.let {
                Dialog(it).apply {
                    setCancelable(false)
                    setCanceledOnTouchOutside(false)
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    window?.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    setContentView(LayoutLoadingBinding.inflate(layoutInflater).root)
                }
            }
        }
    }

    fun showLoading() {
        if (loadingDialog?.isShowing == false) {
            loadingDialog?.show()
        }
    }

    fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun onDestroyView() {
        loadingDialog?.dismiss()
        super.onDestroyView()
    }
}