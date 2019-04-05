package com.example.wafflesale.domain

class Order() {
    var id : Int? = null
    var clientId : Int? = null
    var memberId : Int? = null
    var orderList: MutableList<OrderLine> = mutableListOf()

    fun addOrderLine(orderLine: OrderLine){
        orderList.add(orderLine)
    }

    fun removeOrderLine(orderLine: OrderLine){
        orderList.remove(orderLine)
    }

    fun printOrder(){
        orderList.forEach{o -> println(o)}
    }

    fun getTotalOrderPrice():Float {
        var totalPrice : Float = 0f
        orderList.forEach { o -> totalPrice += o.getTotalOrderLinePrice() }
        return totalPrice
    }
}