package com.example.appdum.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import com.example.appdum.databinding.ActivitySplashBinding
import com.example.appdum.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cargarPantallaPrincipal()
    }

    private fun cargarPantallaPrincipal() {
        Handler(Looper.getMainLooper()).postDelayed({
            val preferencias = this.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
            if(preferencias.getBoolean("logged",false)){
                val nombre = preferencias.getString("nombre","usuario")
                val correo = preferencias.getString("correoElec", "null")
                viewModel.checkAccess(this, correo, nombre)

            }
            else{
                val intPrincipal = Intent(this, LoginActivity::class.java)
                startActivity(intPrincipal)
                this.finish()
            }

        }, 2500)
    }

    fun toasting(message : String){
        Toast.makeText(baseContext,"$message", Toast.LENGTH_SHORT).show()
    }

}