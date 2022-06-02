package com.kotlinapplication

import android.content.Context
import io.objectbox.BoxStore

object MyObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    private fun builder(): Any {
          return store;
    }
}