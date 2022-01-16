package com.example.gameplanet.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameplanet.Entity.EntityProduct
import com.example.gameplanet.HomeActivity
import com.example.gameplanet.R
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.databinding.ItemHomeBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class HomeAdapter(val producList:ArrayList<EntityProduct>, val context: Context): RecyclerView.Adapter<HomeHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomeHolder(inflater.inflate(R.layout.item_home,parent,false))
    }

    override fun getItemCount(): Int {
        return producList.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        Picasso.get()
            .load(producList[position].image)
            .into(holder.imageViewPhoto, object : Callback {
                override fun onSuccess() {
                    Log.d(Constants.LOG_TAG, "success")
                }

                override fun onError(e: Exception?) {
                    Log.d(Constants.LOG_TAG, "error")
                }
            })

        holder.textViewCost.text = producList[position].cost.toString()
        holder.textViewManufacturerProduct.text = producList[position].productManufacturer
        holder.textViewProductName.text =producList[position].productName
    }

}
class HomeHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ItemHomeBinding.bind(view)
    val imageViewPhoto = binding.imageViewPhoto
    val spinnerQuantity = binding.spinnerQuantity
    val textViewCost = binding.textViewCost
    val textViewManufacturerProduct = binding.textViewManufacturerProduct
    val textViewProductName = binding.textViewProductName
}