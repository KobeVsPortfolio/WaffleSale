package com.example.wafflesale.main

import com.example.wafflesale.domain.*

fun main(){
    var org : Club = Club(1, "Plopsaland", Address("Brussel", "straat", 1, "3000"))
    var member = Member("Bert", "Boss", Address("Gent", "straat", 2, "9000"))
    var member2 = Member("Bob", "Boss", Address("Gent", "straat", 2, "9000"))
    var member3: Member = Member("Gert", "Boss", Address("Gent", "straat", 2, "9000"))

    var orderLine = OrderLine(Product(ProductKind.CHOCO_WAFFLE), 5)
    var order = Order()
    order.orderList.add(orderLine)

    var client = Client("Bert", "Boss", Address("Gent", "straat", 2, "9000"),order)

    member.addOrder(order)
    member.addOrder(order)

    member2.addOrder(order)

    org.members.add(member)
    org.members.add(member2)

    org.printOrders()
}