package com.etax.movieapp.core.presentation.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import com.etax.movieapp.R

fun Dialog.showLoadingDialog() = run {
    Dialog(context).requestWindowFeature(Window.FEATURE_NO_TITLE)
    this.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    this.window?.setGravity(Gravity.CENTER)
    this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    this.setContentView(R.layout.dialog_loading)
    this.setCancelable(false)
    this.show()
}

fun Dialog.hideLoadingDialog() = run { if (this.isShowing) this.dismiss() }