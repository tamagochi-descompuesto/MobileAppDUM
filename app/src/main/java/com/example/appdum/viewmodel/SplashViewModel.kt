package com.example.appdum.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.appdum.api.RetrofitInstance
import com.example.appdum.model.UsuarioEnabled
import com.example.appdum.view.LoginActivity
import com.example.appdum.view.MainActivity
import com.example.appdum.view.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel : ViewModel() {
    fun checkAccess(view: SplashActivity, correo: String?, nombre: String?) {
        val usuario = UsuarioEnabled(correo)
        val call = RetrofitInstance.servicioUsuarioApi.enabledUser(usuario)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    //Cuenta de usuario desactivada
                    if (response.body().toString() == "2") {
                        val preferencias = view.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
                        preferencias.edit().clear().commit()
                        val intPrincipal = Intent(view, LoginActivity::class.java)
                        view.startActivity(intPrincipal)
                        view.toasting("Tu cuenta de usuario ha sido desactivada.")
                        view.finish()

                    }
                    //Cuenta de usuario no desactivada
                    else { //Exitoso
                        view.toasting("Bienvenidx, $nombre")
                        val intPrincipal = Intent(view, MainActivity::class.java)
                        view.startActivity(intPrincipal)
                        view.finish()
                    }


                } else {
                    println("Registro fallido")
                    println(response.body().toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                view.toasting("Verifica tu conexión a Internet.")
                if (t.cause.toString() == "java.net.ConnectException: Network is unreachable") {
                    view.toasting("Verifica tu conexión a Internet.")
                } else {
                    view.toasting("El servidor no está disponible.")
                    println("${t.cause.toString()}")
                }
            }
        })
    }

}