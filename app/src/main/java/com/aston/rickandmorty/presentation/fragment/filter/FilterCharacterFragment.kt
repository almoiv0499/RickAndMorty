package com.aston.rickandmorty.presentation.fragment.filter

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.aston.rickandmorty.R

class FilterCharacterFragment : DialogFragment() {

    private val status
        get() = arguments?.getString(ARG_STATUS)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val statusComponents = resources.getStringArray(R.array.character_status)
        val checked =
            if (statusComponents.contains(status)) statusComponents.indexOf(status) else -1

        return AlertDialog.Builder(context)
            .setTitle("Status")
            .setSingleChoiceItems(statusComponents, checked, null)
            .setPositiveButton("Confirm") { dialog, _ ->
                val index = (dialog as AlertDialog).listView.checkedItemPosition
                val status = statusComponents[index]
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY, bundleOf(KEY_STATUS_RESPONSE to status)
                )
            }.setNeutralButton("Cancel") { _, _ ->
                val status = ""
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY, bundleOf(KEY_STATUS_RESPONSE to status)
                )
            }
            .create()
    }

    companion object {
        private val TAG = FilterCharacterFragment::class.java.simpleName
        private const val KEY_STATUS_RESPONSE = "key status response"
        private const val ARG_STATUS = "arg status"
        private val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(fragmentManager: FragmentManager, status: String) {
            val dialogFragment = FilterCharacterFragment()
            dialogFragment.arguments = bundleOf(ARG_STATUS to status)
            dialogFragment.show(fragmentManager, TAG)
        }

        fun setupListener(
            fragmentManager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (String) -> Unit,
        ) {
            fragmentManager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, result ->
                listener.invoke(result.getString(KEY_STATUS_RESPONSE, ""))
            }
        }

    }

}