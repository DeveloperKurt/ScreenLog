package com.developerkurt.screenlog


import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

/**
 * Initializes the module and adds a floating button to the screen.
 * When clicked, it opens a dialog that displays the logcat logs.
 *
 * @param activity Activity to add the button onto - IT IS YOUR RESPONSIBILITY TO CALL THE onDestroyed() TO NOT THE LEAK THE ACTIVITY
 * @param lifecycleOwner CoroutineLifecycleOwner - can be the activity, fragment itself
 * @param filter When the filter switch is enabled, it will only display the logs that contains the Strings in this list
 */
class ScreenLog constructor(private val activity: Activity,
                            private val lifecycleOwner: LifecycleOwner,
                            var filter : List<String> = listOf())
{
    private var moduleView: ScreenLogView? = ScreenLogView(activity,lifecycleOwner,filter)

    fun onDestroyed(){
        moduleView = null
    }

}