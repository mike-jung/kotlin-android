package org.techtown.calc2

class Calc4 : Calculator2 {
    val alias:String = "Happy day!"

    override val name: String
        get() = alias

    override fun add(a:Int, b:Int): Int {
        return a + b
    }

    override fun subtract(a:Int, b:Int): Int {
        println("subtract 메서드 호출됨.")
        return a - b
    }

}