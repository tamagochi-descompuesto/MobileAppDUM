package com.example.appdum.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdum.api.RetrofitInstance
import com.example.appdum.databinding.GestionDonacionFragmentBinding
import com.example.appdum.model.Donacion
import com.example.appdum.model.idDonativo
import com.example.appdum.view.GestionDonacion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionDonacionViewModel : ViewModel() {
    var fecha = MutableLiveData<String>()
    var idDonacion = MutableLiveData<String>()
    var monto = MutableLiveData<String>()
    var facturado = MutableLiveData<String>()
    var donacion = MutableLiveData<Donacion>()

    fun descargarDonaciones(correo: String){
        val call = RetrofitInstance.servicioUsuarioApi.getDonacion(correo)
        call.enqueue(object : Callback<Donacion> {
            override fun onResponse(
                call: Call<Donacion>,
                response: Response<Donacion>
            ) {
                if (response.isSuccessful){
                    donacion.value = response.body()
                    println("Datos descargados: ${response.body()}")
                }
                else{
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Donacion>, t: Throwable) {
                println("Error al descargar los datos ${t.localizedMessage}")
            }

        })

    }

    fun marcarFactura(gestionDonacion: GestionDonacion, idDonativoStr: String, binding: GestionDonacionFragmentBinding) {
        val call = RetrofitInstance.servicioUsuarioApi.facturaDonativo(idDonativo(idDonativoStr))
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    gestionDonacion.toasting("La donación se ha marcado como facturada.")
                    binding.switchFactura.setText("Desmarcar como facturada")
                    binding.tvFacturadoDonacion.text = "Esta donación ya ha sido facturada."
                }
                else{
                    gestionDonacion.toasting("Ocurrió un error. Vuelva a intentarlo.")
                    binding.switchFactura.setText("Marcar como facturada.")
                    binding.switchFactura.isChecked = false
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                gestionDonacion.toasting("Ocurrió un error. Vuelva a intentarlo.")
            }

        })

    }
    fun desMarcarFactura(gestionDonacion: GestionDonacion, idDonativoStr: String, binding: GestionDonacionFragmentBinding) {
        val call = RetrofitInstance.servicioUsuarioApi.unFacturaDonativo(idDonativo(idDonativoStr))
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    gestionDonacion.toasting("La donación se ha marcado como no facturada.")
                    binding.switchFactura.setText("Marcar como facturada.")
                    binding.tvFacturadoDonacion.text = "Esta donación aún no ha sido facturada."
                }
                else{
                    gestionDonacion.toasting("Ocurrió un error. Vuelva a intentarlo.")
                    binding.switchFactura.setText("Desmarcar como facturada.")
                    binding.switchFactura.isChecked = true
                    println("ERROR ${response.body()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                gestionDonacion.toasting("Ocurrió un error. Vuelva a intentarlo.")
            }

        })
    }
}