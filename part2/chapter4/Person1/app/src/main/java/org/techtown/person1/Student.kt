package org.techtown.person1

class Student(val alias:String?) : Person(alias) {

    init {
        println("Student의 init 블록에서 실행됨.")
    }

    constructor(alias:String?, age:Int?, address:String?):this(alias) {
        println("Student의 생성자에서 실행됨.")

        this.age = age
        this.address = address
    }

}