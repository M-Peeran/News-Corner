package com.peeranm.newscorner.core.utils

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(view: View?, data: T, position: Int)
}