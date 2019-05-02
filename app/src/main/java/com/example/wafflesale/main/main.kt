package com.example.wafflesale.main

import com.example.wafflesale.data.MyDBAdapter
import com.example.wafflesale.domain.*

fun main(){
    var myDBAdapter : MyDBAdapter? = null
    myDBAdapter?.addClient("","","","","","", "")

    myDBAdapter?.findAllClients()
    println(myDBAdapter?.findAllClients()?.size)

}