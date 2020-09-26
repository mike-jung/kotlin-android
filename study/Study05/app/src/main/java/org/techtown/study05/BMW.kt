package org.techtown.study05

import android.widget.TextView

class BMW(override val name: String, val output:TextView) : Car {

    override fun doStart() {
        output?.setText("${name}의 doStart 호출됨")
    }

    override fun doRun() {
        output?.setText("${name}의 doRun 호출됨")
    }

    override fun doTurn() {
        output?.setText("${name}의 doTurn 호출됨")
    }

    override fun doStop() {
        output?.setText("${name}의 doStop 호출됨")
    }

}