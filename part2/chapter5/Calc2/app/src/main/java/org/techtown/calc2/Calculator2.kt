package org.techtown.calc2

interface Calculator2 {

    val name:String

    fun add(a:Int, b:Int):Int

    fun subtract(a:Int, b:Int):Int {
        return a - b
    }

    fun multiply(a:Int, b:Int):Int = a * b

    fun divide(a:Int, b:Int) = a / b

}