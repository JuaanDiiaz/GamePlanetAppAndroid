package com.example.gameplanet

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameplanet.Data.Login
import com.example.gameplanet.Tools.Constants
import com.example.gameplanet.Tools.NetworkConnectionState
import com.example.gameplanet.databinding.ActivityLogInBinding
import java.util.regex.Pattern


class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val x = NetworkConnectionState(this)
        binding.buttonLogin.setOnClickListener {
            if(x.checkNetwork()){
                if(binding.editTextEmail.text.isNotEmpty()
                    && binding.editTextPassword.text.isNotEmpty()){
                    if(Constants.checkEmail(binding.editTextEmail.text.toString())){
                        val login = Login(this)
                        login.getIn(binding.editTextEmail.text.toString(),
                                binding.editTextPassword.text.toString())
                    }else{
                        Toast.makeText(this, "Formato de correo invalido",
                                Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, "favor de ingresar los datos completos",
                            Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, R.string.outConn,
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