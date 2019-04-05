package com.example.wafflesale.domain

abstract class Person() {
    open val id : Int? = null
    open val firstName: String? = null
    open val lastName: String? = null
    var fullName = fun(): String { return "$firstName $lastName" }

    fun getFullName(): String {
        return "$firstName $lastName"
    }
}