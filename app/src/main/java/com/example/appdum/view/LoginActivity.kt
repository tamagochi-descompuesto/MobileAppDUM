package com.example.appdum.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.appdum.databinding.ActivityLoginBinding
import com.example.appdum.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_login)
        setContentView(binding.root)

        binding.btnIniciarSesion.setOnClickListener{
            iniciarSesión()
        }

        binding.btnRegistrarse.setOnClickListener{
            registrarse()
        }
        binding.tvPolitica.setOnClickListener {
            redirigirPolitica()
        }
    }

    fun registrarse(){
        val intPrincipal = Intent(this, RegistroActivity::class.java)
        startActivity(intPrincipal)
    }

    fun iniciarSesión(){
        val correo = binding.tvCorreo.text
        val password = binding.tvPassword.text
        if(correo.isNullOrEmpty() or password.isNullOrEmpty()){
            Toast.makeText(baseContext,"No dejes ningún campo vacío.",Toast.LENGTH_LONG).show()
        }
        else{
            val intPrincipal = Intent(this, MainActivity::class.java)
            viewModel.iniciarSesion(this, correo.toString(),password.toString(), intPrincipal)


        }

    }

    fun redirigirPolitica(){
        val url= "https://www.dibujando.org.mx/aviso-de-privacidad/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    fun toasting(message : String){
        Toast.makeText(baseContext,"$message", Toast.LENGTH_SHORT).show()
    }

}