package ua.nanit.exsumo.log

interface LogAdapter {

    fun debug(msg: String)

    fun info(msg: String)

    fun warn(msg: String)

    fun error(msg: String)

}