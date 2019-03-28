package com.example.wafflesale.domain

class Product(productKind: ProductKind) {

    var productKind = productKind
    var price = this.productKind.price

    fun getProductDescription():String{
        if(productKind.equals(ProductKind.MIX)){
            return "Combinatie van Chocolade -en vanillewafels met carré confituur"
        }else{
            return this.productKind.pName
        }
    }

    override fun toString(): String {
        return "product=${productKind.pName} price=$price \n Description=${this.getProductDescription()})"
    }


}