package com.example.appdum.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdum.api.RetrofitInstance
import com.example.appdum.api.servidorWeb
import com.example.appdum.model.UsuarioCompleto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminViewModel : ViewModel() {
    val arrUsuarios = MutableLiveData<List<UsuarioCompleto>>()

    fun descargarUsuarios(){
        val call = RetrofitInstance.servicioUsuarioApi.getUsuarios()
        call.enqueue(object : Callback<List<UsuarioCompleto>>{
            override fun onResponse(
                call: Call<List<UsuarioCompleto>>,
                response: Response<List<UsuarioCompleto>>
            ) {
                if (response.isSuccessful){
                    arrUsuarios.value = response.body()
                    println("Datos descargados: ${response.body()}")
                }
                else{
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<UsuarioCompleto>>, t: Throwable) {
                println("Error al descargar los datos ${t.localizedMessage}")
            }

        })

    }
}