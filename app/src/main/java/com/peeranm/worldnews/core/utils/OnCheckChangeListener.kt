package com.peeranm.worldnews.core.utils

import android.widget.CompoundButton

interface OnCheckChangeListener<T> {
    fun onCheckChange(
        compButton: CompoundButton?,
        data: T,
        isSelected: Boolean,
        position: Int
    )
}