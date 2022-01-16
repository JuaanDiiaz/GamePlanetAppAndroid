package com.example.gameplanet.Entity

data class EntityCustomer (
    var name:String,
    var lastName:String,
    var surName:String,
    var gender:Int,
    var dateOfBirth:String,
    var idMunicipality:Int,
    var email:String,
    var password:String
) {
    constructor():this("","","",0,"",1,"","")
}