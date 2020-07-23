package org.techtown.person2

class Student2 : Person {

    init {
        println("Student의 init 블록에서 실행됨.")
    }

    constructor(alias:String?, age:Int?, address:String?):super(alias) {
        println("Student의 생성자에서 실행됨.")

        this.age = age
        this.address = address
    }
    
}