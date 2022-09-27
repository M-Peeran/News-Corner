package com.peeranm.worldnews.feature_news.utils

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(view: View?, data: T, position: Int)
}