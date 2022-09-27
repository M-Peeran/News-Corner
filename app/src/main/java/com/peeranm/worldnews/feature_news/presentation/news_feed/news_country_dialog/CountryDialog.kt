package com.peeranm.worldnews.feature_news.presentation.news_feed.news_country_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.peeranm.worldnews.core.Constants
import com.peeranm.worldnews.databinding.DialogCountryBinding
import com.peeranm.worldnews.feature_news.utils.CountryAdapter
import com.peeranm.worldnews.feature_news.utils.CountryCode
import com.peeranm.worldnews.feature_news.utils.OnCheckChangeListener

class CountryDialog : DialogFragment(), OnCheckChangeListener<CountryCode> {

    private var _binding: DialogCountryBinding? = null
    private val binding: DialogCountryBinding
    get() = _binding!!

    private var adapter: CountryAdapter? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Choose Country")
            .setView(getDialogView())
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setCanceledOnTouchOutside(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun getDialogView(): View {
        _binding = DialogCountryBinding.inflate(layoutInflater)
        binding.bindList()
        return binding.root
    }

    override fun onCheckChange(
        compButton: CompoundButton?,
        data: CountryCode,
        isSelected: Boolean,
        position: Int
    ) {
        setFragmentResult(
            Constants.KEY_COUNTRY_DIALOG_RESULT_LISTENER,
            bundleOf(Constants.ARG_COUNTRY_DIALOG_RESULT to data)
        )
        dismiss()
    }

    private fun DialogCountryBinding.bindList() {
        adapter = CountryAdapter(this@CountryDialog)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listCountries.adapter = adapter
        listCountries.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }
}