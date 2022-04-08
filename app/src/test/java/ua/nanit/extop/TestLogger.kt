package ua.nanit.extop

import ua.nanit.extop.log.LogAdapter

class TestLogger : LogAdapter {

    override fun debug(msg: String) {
        println("[DEBUG]: $msg")
    }

    override fun info(msg: String) {
        println("[INFO]: $msg")
    }

    override fun warn(msg: String) {
        println("[WARNING]: $msg")
    }

    override fun error(msg: String) {
        println("[ERROR]: $msg")
    }
}