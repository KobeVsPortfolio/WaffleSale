package com.example.wafflesale.domain

enum class Product(val pName:String,val price: Float,val weight: Int) {
    CHOCO_WAFFLE("Chocowafels", 6f, 700),
    VANILLE_WAFFLE( "Vanillewafels", 6f, 700),
    FRANCHIPAN("Franchipan", 6f, 700),
    SQUAREJAM("Carré Confituur",6f, 700),
    MIX("Mix",7f, 800);

    fun getProductDescription():String{
        if(pName.equals(MIX)){
            return "Combinatie van Chocolade -en vanillewafels met carré confituur"
        }else{
            return this.pName
        }
    }

    override fun toString(): String {
        return "product=${pName} price=$price \n Description=${this.getProductDescription()})"
    }


}