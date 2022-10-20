package com.learn.mydiary.util.extension

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun FragmentActivity.showBottomSheet(
    bottomSheetDialogFragment: BottomSheetDialogFragment,
    isCancelable: Boolean = true,
    bundle: Bundle? = null,
    tag: String? = null
) {
    supportFragmentManager.executePendingTransactions()
    val f = supportFragmentManager.findFragmentByTag(tag ?: bottomSheetDialogFragment.javaClass.simpleName)
    if (f == null) {
        bundle?.let {
            bottomSheetDialogFragment.arguments = it
        }
        bottomSheetDialogFragment.isCancelable = isCancelable
        bottomSheetDialogFragment.show(
            supportFragmentManager,
            tag ?: bottomSheetDialogFragment.javaClass.simpleName
        )
    }
}