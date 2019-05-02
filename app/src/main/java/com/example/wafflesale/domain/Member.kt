package com.example.wafflesale.domain

import java.io.Serializable


class Member : Person(), Serializable {
    override var id: Int? = null
    override var firstName: String? = null
    override var lastName: String? = null
    var email : String? = null
    var orders : MutableList<Order> = mutableListOf()
    var imageUrl : Int = 0

    fun addOrder(order: Order){
        orders.add(order)
    }

    fun removeOrder(order: Order) {
        orders.remove(order)
    }

    fun printOrders(){
        orders.forEach{o -> o.printOrder()}
    }

    fun getTotalMemberPrice():Float{
        var totalPrice : Float = 0f
        orders.forEach { o -> totalPrice += o.getTotalOrderPrice() }
        return totalPrice
    }
}