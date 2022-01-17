package com.example.gameplanet

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.core.widget.doOnTextChanged
import com.example.gameplanet.Data.Register
import com.example.gameplanet.Entity.EntityCustomer
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.Tools.NetworkConnectionState
import com.example.gameplanet.databinding.ActivityRegisterBinding
import java.util.*

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var register: Register
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val x = NetworkConnectionState(this)
        if(x.checkNetwork()){
            register = Register(this,binding)
            register.getStates()

            binding.spinnerStates.onItemSelectedListener = this@RegisterActivity

            configure()

        }
        else{
            Toast.makeText(this,R.string.outConn, Toast.LENGTH_LONG).show()
            finish()
        }
    }
    fun configure(){
        binding.editTextDate.setOnClickListener {
            val myCalendar= Calendar.getInstance()
            val year= myCalendar.get(Calendar.YEAR)
            val month= myCalendar.get(Calendar.MONTH)
            val day= myCalendar.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog,
                    DatePickerDialog.OnDateSetListener { view, y, m, d ->
                binding.editTextDate.setText("$y-${twoDigits(m+1)}-${twoDigits(d)}")
            },year,month,day)
            dpd.show()
        }
        binding.editTextPass2.doOnTextChanged { text, start, before, count ->
            if(binding.editTextPass1.text.toString() != binding.editTextPass2.text.toString()){
                binding.editTextPass2.background.setTint(Color.RED)
            }else{
                binding.editTextPass2.background.setTint(Color.GREEN)
            }
        }
        binding.buttonRegisterC.setOnClickListener {
            validaData()
        }
    }
    fun validaData(){
        var message = ""
        var todoOk = true
        if(binding.editTextName.text.isNullOrEmpty()){
            todoOk=false
            message+="Falta nombre \n"
        }
        if(binding.editTextLastName.text.isNullOrEmpty()){
            todoOk=false
            message+="Falta apellido paterno \n"
        }
        if(binding.editTextSurName.text.isNullOrEmpty()){
            todoOk=false
            message+="Falta apellido materno \n"
        }
        if(binding.editTextDate.text.isNullOrEmpty()){
            todoOk=false
            message+="Falta fecha de nacimiento \n"
        }
        if(binding.editTextEmailC.text.isNullOrEmpty()){
            todoOk=false
            message+="Falta correo \n"
        }else{
            if(!Constants.checkEmail(binding.editTextEmailC.text.toString())){
                todoOk=false
                message+="Formato de correo invalido \n"
            }
        }
        if(binding.editTextPass1.text.isNullOrEmpty() || binding.editTextPass2.text.isNullOrEmpty()){
            todoOk=false
            message+="Ingresar contraseña y confirmarla \n"
        }
        if(binding.editTextPass1.text.toString() != binding.editTextPass2.text.toString()){
            todoOk=false
            message+="Las contraseñas no coinciden \n"
        }
        if(todoOk){
            addCustomer()
        }else{
            miDialogo(message).show()
        }
    }
    fun addCustomer(){
        val customer =  EntityCustomer()
        customer.name = binding.editTextName.text.toString()
        customer.lastName = binding.editTextLastName.text.toString()
        customer.surName = binding.editTextSurName.text.toString()
        customer.gender = binding.spinnerGender.selectedItemPosition +1
        customer.dateOfBirth = binding.editTextDate.text.toString()
        customer.idMunicipality = binding.spinnerMunicipalities
            .selectedItem.toString().split(' ')[0].toInt()
        customer.email = binding.editTextEmailC.text.toString()
        customer.password = binding.editTextPass1.text.toString()
        register.registerNow(customer)
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val array = binding.spinnerStates.selectedItem.toString().split(' ')
        val stateId = array[0]
        register.getMunicipalities(stateId)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
    fun twoDigits(number:Int):String{
        return if(number<=9)"0$number" else number.toString()
    }
    private fun miDialogo(message:String): AlertDialog {
        val miAlerta = AlertDialog.Builder(this)

        miAlerta.setTitle("Error - datos faltantes")
        miAlerta.setMessage(message + "\n ¿Desea continuar?")
        miAlerta.setPositiveButton("Sí"){_,_ ->

        }
        miAlerta.setNegativeButton("No"){_,_ ->
            finish()
        }
        return miAlerta.create()
    }
}