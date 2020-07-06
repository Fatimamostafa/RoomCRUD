package com.fatimamostafa.mvvm_template.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), BaseCommunicator {

    fun getContext(): Context = this

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
    }

    override fun startActivity(cls: Class<*>, finishSelf: Boolean) {
        val intent = Intent(getContext(), cls)
        startActivity(intent, finishSelf)
    }

    override fun clearAllAndStartActivity(cls: Class<*>) {
        val intent = Intent(getContext(), cls)
        clearAllAndStartActivity(intent)
    }

}