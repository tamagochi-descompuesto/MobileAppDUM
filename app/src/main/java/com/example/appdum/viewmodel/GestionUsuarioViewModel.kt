package com.example.appdum.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdum.api.RetrofitInstance
import com.example.appdum.databinding.GestionUsuarioFragmentBinding
import com.example.appdum.model.Donacion
import com.example.appdum.model.UsuarioCompleto
import com.example.appdum.model.UsuarioCorreo
import com.example.appdum.view.GestionUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionUsuarioViewModel : ViewModel() {

    var correo = MutableLiveData<String>()
    var nombre = MutableLiveData<String>()
    var rfc = MutableLiveData<String>()
    var fechaRegistro = MutableLiveData<String>()
    var enabled = MutableLiveData<String>()
    var isAdmin = MutableLiveData<String>()
    var arrDonaciones = MutableLiveData<List<Donacion>>()
    fun descargarUsuario(correoElec: String){
        val call = RetrofitInstance.servicioUsuarioApi.getUsuario(correoElec)
        call.enqueue(object : Callback<UsuarioCompleto>{
            override fun onResponse(
                call: Call<UsuarioCompleto>,
                response: Response<UsuarioCompleto>
            ) {
                if(response.isSuccessful){
                    correo.value = response.body()?.correoElec!!
                    nombre.value = response.body()?.nombre!! + " " + response.body()?.apellidoP!! + " " + response.body()?.apellidoM!!
                    rfc.value = response.body()?.rfc!!
                    fechaRegistro.value = response.body()?.fechaRegistro!!
                    enabled.value = response.body()?.enabled!!
                    isAdmin.value = response.body()?.isAdmin!!
                }
            }

            override fun onFailure(call: Call<UsuarioCompleto>, t: Throwable) {
                println("Error ${t.localizedMessage}")
            }

        })

    }

    fun ban(
        correoBan: UsuarioCorreo,
        gestionUsuario: GestionUsuario,
        binding: GestionUsuarioFragmentBinding
    ) {
        val call = RetrofitInstance.servicioUsuarioApi.banUsuario(correoBan)
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    println(response.body())
                    if(response.body() == "0"){
                        gestionUsuario.toasting("El usuario ha sido bloqueado.")
                        binding.switchBan.setText("Desbloquear")
                        binding.tvBaneadoGestion.text = "Usuario bloqueado de la plataforma."
                    }
                    else{
                        gestionUsuario.toasting("Ocurrió un error, intentelo nuevamente.")
                    }

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                gestionUsuario.toasting("Ocurrió un error, intentelo nuevamente.")
                println(t.localizedMessage)
            }

        })
    }

    fun unban(
        correoBan: UsuarioCorreo,
        gestionUsuario: GestionUsuario,
        binding: GestionUsuarioFragmentBinding
    ) {
        val call = RetrofitInstance.servicioUsuarioApi.unbanUsuario(correoBan)
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    gestionUsuario.toasting("El usuario ha sido desbloqueado.")
                    binding.switchBan.setText("Bloquear")
                    binding.tvBaneadoGestion.text = "Usuario con acceso a la plataforma."
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun darAdmin(
        correoAdmin: UsuarioCorreo,
        gestionUsuario: GestionUsuario,
        binding: GestionUsuarioFragmentBinding
    ){
        val call = RetrofitInstance.servicioUsuarioApi.darAdminUsuario(correoAdmin)
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    gestionUsuario.toasting("Ahora el usuario es administrador.")
                    binding.switchAdmin.setText("Degradar")
                    binding.tvEsAdminGestion.text = "Este usuario es administrador."
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun quitarAdmin(
        correoAdmin: UsuarioCorreo,
        gestionUsuario: GestionUsuario,
        binding: GestionUsuarioFragmentBinding
    ){
        val call = RetrofitInstance.servicioUsuarioApi.quitarAdminUsuario(correoAdmin)
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    gestionUsuario.toasting("El usuario ya no es administrador.")
                    binding.switchAdmin.setText("Promover")
                    binding.tvEsAdminGestion.text = "Este usuario no es administrador."
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun descargarDonaciones(correo: String){
        val call = RetrofitInstance.servicioUsuarioApi.getDonaciones(correo)
        call.enqueue(object : Callback<List<Donacion>> {
            override fun onResponse(
                call: Call<List<Donacion>>,
                response: Response<List<Donacion>>
            ) {
                if (response.isSuccessful){
                    arrDonaciones.value = response.body()
                }
                else{
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Donacion>>, t: Throwable) {
                println("Error al descargar los datos ${t.localizedMessage}")
            }

        })

    }
}