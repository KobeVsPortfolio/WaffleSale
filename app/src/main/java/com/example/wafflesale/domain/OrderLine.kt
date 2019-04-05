package com.example.wafflesale.domain

class OrderLine() {
    var product: Product? = null
    var orderId : Int = 0
    var amount: Int = 0
    var id : Int? = null

    fun getTotalOrderLinePrice():Float{
        return this.product?.price!!.times(this.amount)
    }

    override fun toString(): String {
        return "product=$product, amount=$amount, getTotalOrderLinePrice=${getTotalOrderLinePrice()}"
    }


}