package com.fatimamostafa.roomcrud.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), BaseCommunicator {

    private fun getContext(): Context = this

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
    }

    override fun startActivity(intent: Intent, finishSelf: Boolean) {
        startActivity(intent)
        if (finishSelf) {
            finish()
        }
    }

    override fun startActivity(cls: Class<*>, finishSelf: Boolean) {
        val intent = Intent(getContext(), cls)
        startActivity(intent, finishSelf)
    }

    override fun clearAllAndStartActivity(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun clearAllAndStartActivity(cls: Class<*>) {
        val intent = Intent(getContext(), cls)
        clearAllAndStartActivity(intent)
    }

}