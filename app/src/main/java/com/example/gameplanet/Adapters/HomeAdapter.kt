package com.example.gameplanet.Adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gameplanet.CartActivity
import com.example.gameplanet.Data.CartDB
import com.example.gameplanet.Data.Configurations
import com.example.gameplanet.Entity.EntityCart
import com.example.gameplanet.Entity.EntityProduct
import com.example.gameplanet.HomeActivity
import com.example.gameplanet.R
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.databinding.ItemHomeBinding
import com.google.android.material.snackbar.Snackbar
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

        holder.buttonAddToCart.setOnClickListener {
            val useDB =  CartDB(context)
            val conf = Configurations(context)

            val cart = EntityCart()
            cart.idUser=conf.getValueString(Constants.id_user)!!
            cart.quantity=holder.spinnerQuantity.selectedItemPosition +1
            cart.product=producList[position]

            useDB.add(cart)
            myDialog().show()
        }
    }
    private fun myDialog(): AlertDialog {
        val myAlert = AlertDialog.Builder(context)
        myAlert.setTitle("Carrito")
        myAlert.setMessage("Producto agregado exitosamente\n¿desea continuar comprando?")
        myAlert.setPositiveButton("Sí"){ dialogInterface: DialogInterface, i: Int ->

        }
        myAlert.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->
            val intent = Intent(context,CartActivity::class.java)
            context.startActivity(intent)
        }
        return myAlert.create()
    }
}
class HomeHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ItemHomeBinding.bind(view)
    val imageViewPhoto = binding.imageViewPhoto
    val spinnerQuantity = binding.spinnerQuantity
    val textViewCost = binding.textViewCost
    val textViewManufacturerProduct = binding.textViewManufacturerProduct
    val textViewProductName = binding.textViewProductName
    val buttonAddToCart = binding.buttonAddToCart
}