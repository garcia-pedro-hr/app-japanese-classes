package com.phgarcia.msjc.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.phgarcia.msjc.R

@BindingAdapter("module_number")
fun TextView.setModuleNumber(number: Long?) {
    number?.let {
        text = context.getString(R.string.numbered_module, number.toInt())
    }
}