package com.example.gameplanet.Entity

class EntityCart (
        var id:Int,
        var idUser:String,
        var product:EntityProduct,
        var quantity:Int
) {
    constructor():this(0,"",EntityProduct(),0)
}