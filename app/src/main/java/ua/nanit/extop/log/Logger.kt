package ua.nanit.extop.log

object Logger {

    private lateinit var adapter: LogAdapter

    fun init(adapter: LogAdapter) {
        this.adapter = adapter
    }

    fun debug(msg: String) {
        adapter.debug(msg)
    }

    fun info(msg: String) {
        adapter.info(msg)
    }

    fun warn(msg: String) {
        adapter.warn(msg)
    }

    fun error(msg: String) {
        adapter.error(msg)
    }

}