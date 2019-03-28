package com.example.wafflesale.domain

enum class ProductKind(val pName:String,val price: Float,val weight: Int) {
    CHOCO_WAFFLE("Chocowafels", 6f, 700),
    VANILLE_WAFFLE( "Vanillewafels", 6f, 700),
    FRANCHIPAN("Franchipan", 7f, 700),
    SQUAREJAM("Carré Confituur",7f, 700),
    MIX("Mix",7f, 800);

}