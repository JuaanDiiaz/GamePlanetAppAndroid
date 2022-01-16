package com.example.gameplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.gameplanet.Data.Configurations
import com.example.gameplanet.Data.Login
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.Tools.NetworkConnectionState
import com.example.gameplanet.databinding.ActivityLogInBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val login = Login(this)
        val x = NetworkConnectionState(this)
        if(x.checkNetwork()){
            val conf = Configurations(this)
            try {
                if(conf.getValueString(Constants.user)!!.isNotEmpty() && conf.getValueString(Constants.pass)!!.isNotEmpty()){
                    login.getIn(conf.getValueString(Constants.user)!!,conf.getValueString(Constants.pass)!!)
                }else{
                    goToLogin()
                }
            } catch (e: Exception) {
                goToLogin()
            }
        }
        else{
            Toast.makeText(this,R.string.outConn, Toast.LENGTH_LONG).show()
            finish()
        }
    }
    fun goToLogin(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity,LogInActivity::class.java)
            startActivity(intent)
            finish()
        },3000L)
    }
}