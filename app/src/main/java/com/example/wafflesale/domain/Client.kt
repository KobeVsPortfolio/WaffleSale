package com.example.wafflesale.domain

class Client ():Person() {
    override var id: Int? = null
    override var firstName: String? = null
    override var lastName:String? = null
    var orders:List<Order>? = ArrayList()
    var city:String? = null
    var street: String? = null
    var number: String? = null
    var postCode: String? = null

    fun getOrderPrice():Float{
        var total = 0f
        if (orders != null) {
            for(o in orders!!){
                total += o.getTotalOrderPrice()
            }
        }
        return total
    }
}