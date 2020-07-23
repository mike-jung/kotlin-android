package org.techtown.calc1

/*
// 속성 추가
class Person {
    var name: String? = null
    var age: Int? = null
    lateinit var address: String
}
*/

/*
class Person {
    var name: String? = null
    var age: Int? = null
    lateinit var address: String

    init {
        println("기본 생성자를 위한 코드 실행 부분입니다.")
    }
}
*/

class Person(val name:String?) {
    var age: Int? = null
    lateinit var address: String

    init {
        println("기본 생성자를 위한 코드 실행 부분입니다 : ${name}")
    }
}

