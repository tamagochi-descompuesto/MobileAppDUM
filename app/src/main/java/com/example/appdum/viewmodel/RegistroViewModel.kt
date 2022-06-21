package com.example.appdum.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appdum.api.RetrofitInstance
import com.example.appdum.model.Usuario
import com.example.appdum.view.RegistroActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroViewModel: ViewModel() {
    fun enviarUsuario(usuario: Usuario, registroActivity: RegistroActivity) {
        val call = RetrofitInstance.servicioUsuarioApi.enviarUsuario(usuario)
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    println("Registro exitoso")
                    println(response.body().toString())
                    if(response.body().toString()=="1"){//Error
                        registroActivity.toasting("El correo o el RFC ya están registrados.")
                    }
                    else if(response.body().toString()=="0"){ //Exitoso
                        registroActivity.toasting("Registro exitoso, puedes iniciar sesión.")
                        registroActivity.finish()
                    }


                } else {
                    println("Registro fallido")
                    println(response.body().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                registroActivity.toasting("Verifica tu conexión a Internet.")
                if(t.cause.toString() == "java.net.ConnectException: Network is unreachable"){
                    registroActivity.toasting("Verifica tu conexión a Internet.")
                }
                else{
                    registroActivity.toasting("El servidor no está disponible.")
                }
            }
        })
    }
}