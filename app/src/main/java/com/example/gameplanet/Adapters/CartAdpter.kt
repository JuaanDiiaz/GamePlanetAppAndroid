package com.example.gameplanet.Adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameplanet.Data.CartDB
import com.example.gameplanet.Data.Configurations
import com.example.gameplanet.Entity.EntityCart
import com.example.gameplanet.Entity.EntityProduct
import com.example.gameplanet.R
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.databinding.ItemCartBinding
import com.example.gameplanet.databinding.ItemHomeBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CartAdpter(val cartList:ArrayList<EntityCart>, val context: Context): RecyclerView.Adapter<CartHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CartHolder(inflater.inflate(R.layout.item_cart,parent,false))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        Picasso.get()
                .load(cartList[position].product.image)
                .into(holder.imageViewPhoto, object : Callback {
                    override fun onSuccess() {
                        Log.d(Constants.LOG_TAG, "success")
                    }

                    override fun onError(e: Exception?) {
                        Log.d(Constants.LOG_TAG, "error")
                    }
                })

        holder.textViewCost.text = cartList[position].product.cost.toString()
        holder.textViewManufacturerProduct.text = cartList[position].product.productManufacturer
        holder.textViewProductName.text = cartList[position].product.productName
        holder.spinnerQuantity.setSelection(cartList[position].quantity-1)
        holder.buttonDeleteToCart.setOnClickListener {
            val dataBase = CartDB(context)
            dataBase.delete(cartList[position].id)
            cartList.removeAt(position)
            notifyDataSetChanged()
            myDialog().show()
        }
    }
    private fun myDialog(): AlertDialog {
        val myAlert = AlertDialog.Builder(context)
        myAlert.setTitle("Carrito")
        myAlert.setMessage("Producto eliminado exitosamente\n¿desea continuar comprando?")
        myAlert.setPositiveButton("Sí"){ dialogInterface: DialogInterface, i: Int ->
            (context as Activity).finish()
        }
        myAlert.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

        }
        return myAlert.create()
    }
}
class CartHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ItemCartBinding.bind(view)
    val imageViewPhoto = binding.imageViewPhoto
    val spinnerQuantity = binding.spinnerQuantity
    val textViewCost = binding.textViewCost
    val textViewManufacturerProduct = binding.textViewManufacturerProduct
    val textViewProductName = binding.textViewProductName
    val buttonDeleteToCart = binding.buttonDeleteToCart
}