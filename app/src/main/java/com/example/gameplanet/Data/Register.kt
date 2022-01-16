package com.example.gameplanet.Data

import android.R
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gameplanet.Entity.EntityCustomer
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.databinding.ActivityRegisterBinding
import org.json.JSONObject

class Register(val context: Context, val binding: ActivityRegisterBinding) {
    private lateinit var queue: RequestQueue
    private var statesList = ArrayList<String>()
    private var municipalitiesList = ArrayList<String>()

    fun getStates(){
        queue= Volley.newRequestQueue(context)
        val url_co= "${Constants.url}/api/GamePlanet/GetStates"
        statesList=ArrayList<String>()
        val stringRequest = StringRequest(
            Request.Method.GET,url_co,
            { response ->
                Log.d(Constants.LOG_TAG, "response is $response")
                val jsonObject = JSONObject(response)
                val requestArray = jsonObject.getJSONArray("values")
                Log.d("UdelP", "Message is ${jsonObject["message"]}")
                for (i in 0 until requestArray.length()) {
                    statesList.add(
                        "${
                            requestArray.getJSONObject(i).getString("id")
                        } ${requestArray.getJSONObject(i).getString("stateName")}"
                    )
                }
                setDataSpinnerStates()
            },
            { error ->
                Log.d(Constants.LOG_TAG, "error is $error")
            }
        )
        queue.add(stringRequest)
    }
    fun getMunicipalities(id:String){
        queue= Volley.newRequestQueue(context)
        val url_co= "${Constants.url}/api/GamePlanet/GetMunicipalities/$id"
        municipalitiesList=ArrayList<String>()
        val stringRequest = StringRequest(
            Request.Method.GET,url_co,
            { response ->
                Log.d(Constants.LOG_TAG, "response is $response")
                val jsonObject = JSONObject(response)
                val requestArray = jsonObject.getJSONArray("values")
                Log.d("UdelP", "Message is ${jsonObject["message"]}")
                for (i in 0 until requestArray.length()) {
                    municipalitiesList.add(
                        "${
                            requestArray.getJSONObject(i).getString("id")
                        } ${requestArray.getJSONObject(i).getString("municipalityName")}"
                    )
                }
                setDataSpinnermunicipalities()
            },
            { error ->
                Log.d(Constants.LOG_TAG, "error is $error")
            }
        )
        queue.add(stringRequest)
    }

    fun setDataSpinnerStates(){
        val adapter = ArrayAdapter<String>(context!!, R.layout.simple_list_item_1,statesList)
        binding.spinnerStates.adapter = adapter
    }
    fun setDataSpinnermunicipalities(){
        val adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1,municipalitiesList)
        binding.spinnerMunicipalities.adapter = adapter
    }

    fun registerNow(customer: EntityCustomer){
        queue= Volley.newRequestQueue(context)
        val url_co= "${Constants.url}/api/GamePlanet/Customer"
        val jsonBody = JSONObject()
        jsonBody.put("name",customer.name)
        jsonBody.put("lastName",customer.lastName)
        jsonBody.put("surName",customer.surName)
        jsonBody.put("gender",customer.gender)
        jsonBody.put("dateOfBirth",customer.dateOfBirth)
        jsonBody.put("idMunicipality",customer.idMunicipality)
        jsonBody.put("email",customer.email)
        jsonBody.put("password",customer.password)


        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,url_co,jsonBody,
            { response ->
                Log.d(Constants.LOG_TAG,"response is $response")
                Toast.makeText(context,"${response["message"]}", Toast.LENGTH_SHORT).show()
                if(response["values"] == "Registro exitoso"){
                    val login = Login(context)
                    login.getIn(customer.email,customer.password)
                }else{
                    Toast.makeText(context,"${response["message"]}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.d(Constants.LOG_TAG,"error is $error")
                Toast.makeText(context,"$error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
}