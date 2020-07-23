package org.techtown.person2

import android.widget.TextView

class Student(val alias:String?) : Person(alias) {

    init {
        println("Student의 init 블록에서 실행됨.")
    }

    constructor(alias:String?, age:Int?, address:String?):this(alias) {
        println("Student의 생성자에서 실행됨.")

        this.age = age
        this.address = address
    }

    override fun walk(speed:Int, output: TextView) {
        output.setText("학생이 ${speed} 속도로 걸어갑니다.")
    }

    fun run(speed:Int=0, output: TextView) {
        output.setText("학생이 ${speed} 속도로 뛰어갑니다.")
    }

}