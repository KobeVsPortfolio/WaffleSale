package com.example.wafflesale.domain

abstract class Person(firstName: String, lastName: String, address: Address) {
    open val firstName: String = firstName
    open val lastName: String = lastName
    open val address : Address = address
    var fullName = fun(): String { return "$firstName $lastName" }

    fun getFullName(): String {
        return "$firstName $lastName"
    }
}