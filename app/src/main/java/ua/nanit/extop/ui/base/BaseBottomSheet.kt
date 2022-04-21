package ua.nanit.extop.ui.base

import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheet : BottomSheetDialogFragment() {

    private var opened = false

    fun show(fragmentManager: FragmentManager) {
        if (!opened) {
            show(fragmentManager, tag())
            opened = true
        }
    }

    fun hide() {
        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.isHideable = true
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onStop() {
        super.onStop()
        opened = false
    }

    protected open fun tag(): String {
        return "BottomSheetTag"
    }
}