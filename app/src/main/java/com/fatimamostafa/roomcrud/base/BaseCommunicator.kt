package com.fatimamostafa.roomcrud.base

import android.content.Intent

interface BaseCommunicator {

    fun startActivity(intent: Intent, finishSelf: Boolean)

    fun startActivity(cls: Class<*>, finishSelf: Boolean)

    fun clearAllAndStartActivity(intent: Intent)

    fun clearAllAndStartActivity(cls: Class<*>)

}