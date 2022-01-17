package com.example.gameplanet.Data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gameplanet.HomeActivity
import com.example.gameplanet.R
import com.example.gameplanet.Tools.Constants
import org.json.JSONObject


class Login(val context: Context) {
    private lateinit var queue: RequestQueue

    fun getIn(email: String, pass: String){
        queue= Volley.newRequestQueue(context)

        val stringRequest = StringRequest(Request.Method.GET, Constants.url +
                "/api/GamePlanet/Authenticate" +
                "?email=${email}&password=${pass}",
            { response ->
                val jsonObject = JSONObject(response)
                val values: String = jsonObject.getString("values")

                if (!values.isNullOrEmpty() && values != "null") {
                    val x = Configurations(context)
                    x.save(Constants.user, email)
                    x.save(Constants.pass, pass)
                    x.save(Constants.id_user, values.substring(0, 1))

                    Log.d(Constants.LOG_TAG, "val is $values")

                    Toast.makeText(
                        context,
                        "Bienvenido ${values.substring(2, values.length)}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val i = Intent(context, HomeActivity::class.java).apply {
                        putExtra(Constants.id_user, values.substring(0, 1))
                    }
                    context.startActivity(i)
                    (context as Activity).finish()
                } else {
                    Toast.makeText(context, R.string.logFail, Toast.LENGTH_SHORT).show()
                    getOut()
                }
            },
            { error ->
                Log.d(Constants.LOG_TAG, "error is $error")
            }

        )
        queue.add(stringRequest)
    }
    fun getOut(){
        val x=Configurations(context)
        x.save(Constants.user, "")
        x.save(Constants.pass, "")
        x.save(Constants.id_user, "")
    }
}