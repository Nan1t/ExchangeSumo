package ua.nanit.extop.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ua.nanit.extop.log.Logger

import java.util.concurrent.atomic.AtomicBoolean

class SingleEvent<T> : MutableLiveData<T>() {

    private val activated = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) {
            if (!activated.get()) {
                activated.set(true)
                observer.onChanged(it)
            }
        }
    }

    override fun postValue(value: T) {
        activated.set(false)
        super.postValue(value)
    }

    override fun setValue(value: T) {
        activated.set(false)
        super.setValue(value)
    }
}