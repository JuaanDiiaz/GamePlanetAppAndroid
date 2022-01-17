package com.example.gameplanet.Contracts

import android.provider.BaseColumns

object CartContract {
    object Entry: BaseColumns {
        const val TABLE_NAME = "CTL_CART"
        const val  COLUMN_NAME_userId ="userId"
        const val  COLUMN_NAME_productName ="productName"
        const val  COLUMN_NAME_productManufacturer ="productManufacturer"
        const val  COLUMN_NAME_productCost ="productCost"
        const val  COLUMN_NAME_productQuantity ="productQuantity"
        const val  COLUMN_NAME_productImage ="productImage"
    }
}