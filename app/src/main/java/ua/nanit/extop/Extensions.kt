package ua.nanit.extop

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.requireCompatActionBar() : ActionBar? {
    val activity = requireActivity() as AppCompatActivity
    return activity.supportActionBar
}