package com.example.gameplanet.Data

import android.content.Context
import android.content.SharedPreferences
import com.example.gameplanet.Tools.Constants

class Configurations (val context: Context){
    val sharedPrefe: SharedPreferences =context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun save(keyName:String,value:String){
        val editor = sharedPrefe.edit()
        editor.putString(keyName,value)
        editor.commit()
    }
    fun getValueString(keyName:String):String?{
        return sharedPrefe.getString(keyName,null)
    }
    fun clearData(){
        val editor = sharedPrefe.edit()
        editor.clear()
        editor.commit()
    }
}