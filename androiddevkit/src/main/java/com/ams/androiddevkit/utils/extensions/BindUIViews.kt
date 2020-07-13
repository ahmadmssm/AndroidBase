@file:Suppress("unused")

package com.ams.androiddevkit.utils.extensions

import android.widget.CheckBox
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding.widget.RxCompoundButton
import com.jakewharton.rxbinding.widget.RxTextView

fun TextInputEditText.bindTo(setValue: (String) -> Unit) {
    RxTextView
        .textChanges(this)
        .skip(1)
        .subscribe { charSequence -> setValue(charSequence.toString()) }
}

fun EditText.bindTo(setValue: (String) -> Unit) {
    RxTextView
        .textChanges(this)
        .skip(1)
        .subscribe{ charSequence -> setValue(charSequence.toString()) }
}

fun CheckBox.bindTo(setValue:(Boolean) -> Unit) {
    RxCompoundButton
        .checkedChanges(this)
        .subscribe { isChecked -> setValue(isChecked) }
}