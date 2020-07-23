package org.techtown.customlayout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

class Part1 : LinearLayout {

    constructor(context: Context?) : super(context) {
        onInit(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        onInit(context)
    }

    fun onInit(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.part1, this, true)
    }

}