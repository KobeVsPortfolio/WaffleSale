package com.example.wafflesale.domain

import java.io.Serializable

class Address(val city:String, val street: String, val number: Int, val postCode: String) : Serializable {
}