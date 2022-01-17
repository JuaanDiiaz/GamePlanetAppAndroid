package com.example.gameplanet

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameplanet.Adapters.CartAdpter
import com.example.gameplanet.Data.CartDB
import com.example.gameplanet.Data.Configurations
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    val datBase = CartDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCost(this,binding)

        loadRecycler()
    }
    fun loadRecycler(){
        val list = datBase.getAllOfUser(
                Configurations(this).getValueString(Constants.id_user)!!.toInt())
        val adapter = CartAdpter(list,this,binding)
        val lineraLayout =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.rvwCart.layoutManager=lineraLayout
        binding.rvwCart.setHasFixedSize(true)
        binding.rvwCart.adapter=adapter
    }
    companion object {
        fun setCost(context: Context,binding:ActivityCartBinding){
            val datBase = CartDB(context)
            val cartList= datBase.getAllOfUser(
                    Configurations(context).getValueString(Constants.id_user)!!.toInt())
            var total:Double=0.0
            cartList.forEach {
                total += (it.quantity * it.product.cost)
            }
            binding.textViewTotal.text="Total: $$total"
        }
    }
}