package com.peeranm.newscorner.core.utils

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject

fun Fragment.setActionbarTitle(@StringRes stringRes: Int) {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(stringRes)
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.handleOnBackPressed() {
    requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    })
}

inline fun  <reified T> ResponseBody.getResponseObject(): T {
    val gson = Gson()
    val jsonObject = JSONObject(charStream().readText())
    return gson.fromJson(jsonObject.toString(), T::class.java)
}

fun <T> LifecycleOwner.collectWithLifecycle(flow: Flow<T>, collect: FlowCollector<T>) =
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }


fun <T> LifecycleOwner.collectLatestWithLifecycle(flow: Flow<T>, collect: suspend (T) -> Unit) =
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }

fun String.getDateLabel(): String {
    if (isEmpty()) return this
    this.split('T')
        .first()
        .split('-')
        .let { chunks ->
            return buildString {
                append(Months.values().find { it.number == chunks[1] }?.name)
                append(" ${chunks[2]}, ")
                append(chunks[0])
            }
        }
}