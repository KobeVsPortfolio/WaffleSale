package com.example.wafflesale.domain

class OrderLine(var product: Product, var amount: Int) {

    fun getTotalOrderLinePrice():Float{
        return this.product?.price!!.times(this.amount)
    }

    override fun toString(): String {
        return "product=$product, amount=$amount, getTotalOrderLinePrice=${getTotalOrderLinePrice()}"
    }


}