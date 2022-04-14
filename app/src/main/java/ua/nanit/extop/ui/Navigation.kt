package ua.nanit.extop.ui

interface Navigation {

    fun navigate(actionId: Int)

    fun navigateUp()

    fun show()

    fun hide()

}