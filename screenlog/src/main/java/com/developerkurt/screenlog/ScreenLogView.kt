package com.developerkurt.screenlog

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.FloatingActionButton


internal class ScreenLogView(private val activity: Activity, private val lifecycleOwner: LifecycleOwner, var filter : List<String>) : RelativeLayout(activity.baseContext)
{

    private val moduleDialog = ScreenLogDialog(activity,lifecycleOwner,filter)
    private val showDialodFab: FloatingActionButton

    init
    {
        val parent = activity.findViewById<View>(android.R.id.content) as ViewGroup
        val inflater = LayoutInflater.from(activity)


        showDialodFab = inflater.inflate(R.layout.module_fab, parent, false) as FloatingActionButton
        showDialodFab.setOnClickListener {
            moduleDialog.displayDialog()
        }

        val margin = resources.getDimension(R.dimen.fab_margin).toInt()
        val layoutParams: RelativeLayout.LayoutParams = generateDefaultLayoutParams() as LayoutParams
        layoutParams.addRule(ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(ALIGN_PARENT_RIGHT)
        layoutParams.setMargins(margin, margin, margin, margin)
        showDialodFab.layoutParams = layoutParams

        addView(showDialodFab)
        parent.addView(this)
    }


}