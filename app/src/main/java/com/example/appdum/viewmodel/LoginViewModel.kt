package com.example.appdum.viewmodel

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.example.appdum.api.RetrofitInstance
import com.example.appdum.model.UsuarioDatosInicio
import com.example.appdum.model.UsuarioRecibe
import com.example.appdum.view.LoginActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    fun iniciarSesion(view: LoginActivity, correo: String, password: String, accion: Intent) {
        var usuario = UsuarioDatosInicio(correo, password)
        val usuarioJson = Gson().toJson(usuario)
        println(usuarioJson)
        val call = RetrofitInstance.servicioUsuarioApi.iniciarSesion(usuario)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    if (response.body().toString() == "1") {//Error
                        view.toasting("El correo electrónico o la contraseña ingresados son incorrectos.")
                    }
                    else if(response.body().toString() == "2"){
                        view.toasting("Tu cuenta de usuario ha sido desactivada.")
                    }
                    else{ //Exitoso
                        val gson = Gson()
                        val datosUsuario:UsuarioRecibe = gson.fromJson(response.body().toString(),UsuarioRecibe::class.java)
                        val preferencias = view.getSharedPreferences("datosUsuario", MODE_PRIVATE)
                        var isAdmin = false
                        println("SERE ADMIN ${datosUsuario.isAdmin.toString()}")
                        if(datosUsuario.isAdmin.toString() == "0"){
                            isAdmin = true
                        }
                        preferencias.edit{
                            putString("nombre",datosUsuario.nombre)
                            putString("apellidoP",datosUsuario.apellidoP)
                            putString("apellidoM",datosUsuario.apellidoM)
                            putString("correoElec",datosUsuario.correoElec)
                            putString("rfc",datosUsuario.rfc)
                            putBoolean("logged", true)
                            putBoolean("isAdmin", isAdmin)
                            commit()
                        }
                        view.toasting("Bienvenidx, ${datosUsuario.nombre}.")
                        view.startActivity(accion)
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
                }
            }
        })
    }

}