package com.example.wafflesale.domain

class Club(val id : Int, val name: String, val address:Address) {

    var members : MutableList<Member> = mutableListOf()

    fun addMember(member: Member){
        members.add(member)
    }

    fun removeMember(member: Member){
        members.remove(member)
    }

    fun printOrders(){
        members.forEach { m ->
            println("These are the orders of ${m.firstName} ${m.lastName}: ")
            m.printOrders()}
    }

    fun getAllOrders():MutableList<MutableList<Order>>{
        var totalOrders : MutableList<MutableList<Order>> = mutableListOf()
        members.forEach { m -> totalOrders.plusAssign(m.orders) }
        return totalOrders
    }

    fun getTotalPrice():Float{
        var totalSales : Float = 0f
        members.forEach { m -> totalSales += m.getTotalMemberPrice() }
        return totalSales
    }

}