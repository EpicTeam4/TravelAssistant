package com.example.travelassistant.core.commands

import androidx.lifecycle.MutableLiveData
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

class CommandsLiveData<T> : MutableLiveData<Queue<T>>() {
    fun onNext(value: T) {
        val commands = getValue() ?: ConcurrentLinkedQueue<T>()
        commands.add(value)
        setValue(commands)
    }
}