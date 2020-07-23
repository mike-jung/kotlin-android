package org.techtown.calc1

class Person3(val name:String?) {
    var age: Int? = null
    lateinit var address: String

    init {
        println("기본 생성자를 위한 코드 실행 부분입니다 : ${name}")
    }

    constructor(name:String?, age:Int?):this(name) {
        println("두번째 생성자 호출됨.")

        this.age = age
    }

    constructor(name:String?, age:Int?, address:String):this(name) {
        println("세번째 생성자 호출됨.")

        this.age = age
        this.address = address
    }

}