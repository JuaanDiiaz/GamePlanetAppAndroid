package com.example.gameplanet.Entity

data class EntityProduct(
    var id:Int,
    var productName:String,
    var productManufacturer:String,
    var cost:Double,
    var image:String,
    var description:String
) {
    constructor():this(0,"","",0.0,"","")
}