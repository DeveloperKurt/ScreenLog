package com.developerkurt.screenlog

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.liveData

import kotlinx.coroutines.Dispatchers
import java.lang.Thread.sleep


class ScreenLogDialog internal constructor(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
    var filter: List<String>
) {


    private val alertDialog: AlertDialog


    private val logsTv: TextView
    private val filterSwitch: Switch

    private var unfilteredText: String = ""
    private var filteredText: String = ""


    init {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.ModuleDialog)


        val dialogView: View = activity.layoutInflater.inflate(R.layout.screenlog_dialog, null)

        logsTv = dialogView.findViewById(R.id.tvLogs)
        filterSwitch = dialogView.findViewById(R.id.filter_switch)


        builder.setView(dialogView)
        logsTv.isVerticalScrollBarEnabled = true
        builder.setCancelable(true)
        logCatOutput().observe(lifecycleOwner, Observer { logMessage ->
            unfilteredText += "\n\n $logMessage"

            filter.forEach {
                if (logMessage?.contains(it, true) == true) {
                    filteredText += "\n\n $logMessage"
                }

                logsTv.text = if (filterSwitch.isChecked) {
                    filteredText
                } else {
                    unfilteredText
                }
            }
        })

        filterSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                logsTv.text = filteredText
            } else {
                logsTv.text = unfilteredText
            }
        })
        alertDialog = builder.create()



    }


    fun displayDialog() {
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

    var i: Int = 0
    fun logCatOutput() =
        liveData(lifecycleOwner.lifecycle.coroutineScope.coroutineContext + Dispatchers.IO) {
            Runtime.getRuntime().exec("logcat -c")  //Flushes the previous logs
            Runtime.getRuntime().exec("logcat")
                .inputStream
                .bufferedReader()
                .useLines { lines ->
                    lines.forEach { line -> emit(line) }
                }

        }
}