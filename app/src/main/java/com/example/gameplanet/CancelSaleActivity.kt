package com.example.gameplanet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.Tools.NetworkConnectionState
import com.example.gameplanet.databinding.ActivityCancelSaleBinding
import org.json.JSONObject

class CancelSaleActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCancelSaleBinding
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCancelSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val x = NetworkConnectionState(this)
        if(x.checkNetwork()){
            binding.buttonCancel.setOnClickListener {
                saveData()
            }
        }
        else{
            Toast.makeText(this,R.string.outConn, Toast.LENGTH_LONG).show()
            finish()
        }
    }
    fun saveData(){
        if(!binding.editTextUser.text.isNullOrEmpty() &&
            !binding.editTextIdSale.text.isNullOrEmpty() &&
            !binding.editTextPasswordC.text.isNullOrEmpty())
        {
            cancelSale()
        }else{
            Toast.makeText(this,"Favor de completar todos los datos",Toast.LENGTH_LONG).show()
        }
    }
    fun cancelSale(){
        queue= Volley.newRequestQueue(this)
        val url_co= "${Constants.url}/api/GamePlanet/CancelOnlineSale/${binding.editTextIdSale
            .text.toString()}"
        val jsonBody = JSONObject()
        jsonBody.put("email",binding.editTextUser.text.toString())
        jsonBody.put("password",binding.editTextPasswordC.text.toString())

        val jsonObjectRequest = JsonObjectRequest(Request.Method.PUT,url_co,jsonBody,
            { response ->
                Log.d(Constants.LOG_TAG,"response is $response")
                val message = response["message"]
                Toast.makeText(this,message.toString(),Toast.LENGTH_SHORT).show()
                if(message.toString().equals("Se canceló la venta")){
                    finish()
                }
            },
            { error ->
                Log.d(Constants.LOG_TAG,"error is $error")
            }
        )
        queue.add(jsonObjectRequest)
    }
}