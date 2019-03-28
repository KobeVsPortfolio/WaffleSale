package com.example.wafflesale.domain

class Client (override val firstName: String, override val lastName:String, override val address:Address, val order:Order):Person(firstName, lastName, address) {

    fun getOrderPrice():Float{
        return this.order.getTotalOrderPrice()
    }
}