package com.example.gameplanet.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.gameplanet.Contracts.CartContract

class ConnectionDB(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_STUDENTS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_STUDENTS)
        onCreate(db)
    }
    fun openConnection(typeConnection:Int): SQLiteDatabase {
        return when(typeConnection){
            MODE_WRITE->{
                writableDatabase
            }
            MODE_READ->{
                readableDatabase
            }
            else->{
                readableDatabase
            }
        }
    }
    companion object{
        const val DATABASE_NAME="GAME_PLANET_DB"
        const val DATABASE_VERSION=1
        const val CREATE_TABLE_STUDENTS = "CREATE TABLE ${CartContract.Entry.TABLE_NAME} " +
                "( ${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${CartContract.Entry.COLUMN_NAME_userId} INTEGER, " +
                "${CartContract.Entry.COLUMN_NAME_productName} VARCHAR(100), " +
                "${CartContract.Entry.COLUMN_NAME_productManufacturer} VARCHAR(200), " +
                "${CartContract.Entry.COLUMN_NAME_productCost} DECIMAL, " +
                "${CartContract.Entry.COLUMN_NAME_productImage} VARCHAR(500), " +
                "${CartContract.Entry.COLUMN_NAME_productQuantity} INTEGER) "
        const val DROP_TABLE_STUDENTS="DROP TABLE IF EXISTS ${CartContract.Entry.TABLE_NAME}"
        const val MODE_WRITE=1
        const val MODE_READ=2
    }
}