package com.example.gameplanet.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.gameplanet.Contracts.CartContract
import com.example.gameplanet.Entity.EntityCart
import com.example.gameplanet.Tools.Constants

class CartDB(val context: Context) {
    val conectionDb=ConnectionDB(context)
    private lateinit var db: SQLiteDatabase

    fun add(cart:EntityCart):Long{
        db=conectionDb.openConnection(ConnectionDB.MODE_WRITE)
        val values = ContentValues().apply {
            put(CartContract.Entry.COLUMN_NAME_userId,cart.idUser)
            put(CartContract.Entry.COLUMN_NAME_productName,cart.product.productName)
            put(CartContract.Entry.COLUMN_NAME_productManufacturer,cart.product.productManufacturer)
            put(CartContract.Entry.COLUMN_NAME_productCost,cart.product.cost)
            put(CartContract.Entry.COLUMN_NAME_productQuantity,cart.quantity)
            put(CartContract.Entry.COLUMN_NAME_productImage,cart.product.image)
        }
        return db.insert(CartContract.Entry.TABLE_NAME,null,values)
    }
    fun update(cart:EntityCart):Int{
        db=conectionDb.openConnection(ConnectionDB.MODE_WRITE)
        val values = ContentValues().apply {
            put(CartContract.Entry.COLUMN_NAME_userId,cart.idUser)
            put(CartContract.Entry.COLUMN_NAME_productName,cart.product.productName)
            put(CartContract.Entry.COLUMN_NAME_productManufacturer,cart.product.productManufacturer)
            put(CartContract.Entry.COLUMN_NAME_productCost,cart.product.cost)
            put(CartContract.Entry.COLUMN_NAME_productQuantity,cart.quantity)
            put(CartContract.Entry.COLUMN_NAME_productImage,cart.product.image)
        }
        val where = "${BaseColumns._ID} =?"
        val arg = arrayOf(cart.id.toString())
        return db.update(CartContract.Entry.TABLE_NAME,values,where,arg)
    }
    fun delete(id:Int):Int{
        db=conectionDb.openConnection(ConnectionDB.MODE_WRITE)
        val where = "${BaseColumns._ID} =?"
        val arg = arrayOf(id.toString())
        return db.delete(CartContract.Entry.TABLE_NAME,where,arg)
    }
    fun getAllOfUser(id:Int):ArrayList<EntityCart>{
        val cartList = ArrayList<EntityCart>()
        db=conectionDb.openConnection(ConnectionDB.MODE_READ)
        val projection = arrayOf(BaseColumns._ID,
                CartContract.Entry.COLUMN_NAME_userId,
                CartContract.Entry.COLUMN_NAME_productName,
                CartContract.Entry.COLUMN_NAME_productManufacturer,
                CartContract.Entry.COLUMN_NAME_productCost,
                CartContract.Entry.COLUMN_NAME_productQuantity,
                CartContract.Entry.COLUMN_NAME_productImage)
        val where = "${CartContract.Entry.COLUMN_NAME_userId} =?"
        val arg = arrayOf(id.toString())
        val cursor=db.query(CartContract.Entry.TABLE_NAME,projection,where,arg,null,null,null)
        if(cursor.moveToFirst()){
            do{
                val cart =EntityCart()
                cart.id=cursor.getInt(0)
                cart.idUser=cursor.getInt(1).toString()
                cart.product.productName=cursor.getString(2)
                cart.product.productManufacturer=cursor.getString(3)
                cart.product.cost=cursor.getDouble(4)
                cart.quantity=cursor.getInt(5)
                cart.product.image=cursor.getString(6)

                cartList.add(cart)
            }while (cursor.moveToNext())
        }
        else
        {
            Log.d(Constants.LOG_TAG,"Sin valores")
        }
        return cartList
    }
    fun getAll(){
        db=conectionDb.openConnection(ConnectionDB.MODE_READ)
        val projection = arrayOf(BaseColumns._ID,
                CartContract.Entry.COLUMN_NAME_userId,
                CartContract.Entry.COLUMN_NAME_productName,
                CartContract.Entry.COLUMN_NAME_productManufacturer,
                CartContract.Entry.COLUMN_NAME_productCost,
                CartContract.Entry.COLUMN_NAME_productQuantity,
                CartContract.Entry.COLUMN_NAME_productImage)
        val sortOrder = "${CartContract.Entry.COLUMN_NAME_productName} DESC"
        val cursor=db.query(CartContract.Entry.TABLE_NAME,projection,null,null,null,null,sortOrder)
        if(cursor.moveToFirst()){
            do{
                val id:Long=cursor.getLong(0)
                val userId=cursor.getInt(1)
                val productName=cursor.getString(2)
                val productManufacturer=cursor.getString(3)
                val productCost=cursor.getDouble(4)
                val quantity=cursor.getInt(5)
                val image=cursor.getString(6)
                Log.d(Constants.LOG_TAG,"$id | $userId | $productName | $productManufacturer | $productCost | $quantity | $image" )
            }while (cursor.moveToNext())
        }
        else
        {
            Log.d(Constants.LOG_TAG,"Sin valores")
        }
    }
}