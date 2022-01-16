package com.example.gameplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gameplanet.Adapters.HomeAdapter
import com.example.gameplanet.Data.Configurations
import com.example.gameplanet.Data.Login
import com.example.gameplanet.Entity.EntityProduct
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.Tools.NetworkConnectionState
import com.example.gameplanet.databinding.ActivityHomeBinding
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private lateinit var queue: RequestQueue
    var productArray = ArrayList<EntityProduct>()
    private lateinit var id_User:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id_User=intent.getStringExtra(Constants.id_user)!!

        val x = NetworkConnectionState(this)
        if(x.checkNetwork()){
            setData()
        }
        else{
            Toast.makeText(this,R.string.outConn, Toast.LENGTH_LONG).show()
            finish()
        }
    }
    fun setData(){
        queue= Volley.newRequestQueue(this)
        val url_co= "${Constants.url}/api/GamePlanet/GetProducts"

        productArray=ArrayList<EntityProduct>()
        val stringRequest = StringRequest(
            Request.Method.GET,url_co,
            { response ->
                val jsonObject = JSONObject(response)
                val productArrayL = jsonObject.getJSONArray("values")
                for (i in 0 until productArrayL.length()){
                    val product = EntityProduct()
                    product.id=productArrayL.getJSONObject(i).getInt("id")
                    product.productName=productArrayL.getJSONObject(i).getString("productName")
                    product.productManufacturer=productArrayL.getJSONObject(i).getString("productManufacturer")
                    product.cost = productArrayL.getJSONObject(i).getDouble("cost")
                    product.image=productArrayL.getJSONObject(i).getString("image")
                    product.description=productArrayL.getJSONObject(i).getString("desciption")

                    productArray.add(product)
                }
                loadRecycler()
            },
            { error ->
                Log.d(Constants.LOG_TAG,"error is $error")
            }
        )
        queue.add(stringRequest)
    }
    private fun loadRecycler(){
        val adapter = HomeAdapter(productArray,this)

        val linearLayout = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        binding.rvwHome.layoutManager=linearLayout
        binding.rvwHome.setHasFixedSize(true)
        binding.rvwHome.adapter=adapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itmLogOut->{
                val x = Login(this)
                    x.getOut()
                Toast.makeText(this,"Te esperamos pronto", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.itmCancelSale->{
                val i = Intent(this,CancelSaleActivity::class.java)
                startActivity(i)
            }
            R.id.itmCart->{
                Toast.makeText(this,"Activity en proceso", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}