package com.example.gameplanet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gameplanet.Data.Login
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonLogin.setOnClickListener {
            if(binding.editTextEmail.text.isNotEmpty()
                && binding.editTextPassword.text.isNotEmpty()){
                val login = Login(this)
                login.getIn(binding.editTextEmail.text.toString(),
                    binding.editTextPassword.text.toString())
            }else{
                Toast.makeText(this,"favor de ingresar los datos completos",
                    Toast.LENGTH_LONG).show()
            }
        }
        binding.buttonRegister.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}