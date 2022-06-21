package com.example.appdum.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.appdum.R
import com.example.appdum.api.RetrofitInstance
import com.example.appdum.model.UsuarioEditar
import com.example.appdum.view.nav_editarPerfil
import com.google.android.material.internal.NavigationMenuItemView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NavEditarPerfilViewModel : ViewModel() {

    fun guardar(
        navEditarperfil: nav_editarPerfil,
        nombre: String,
        apellidoP: String,
        apellidoM: String,
        correo: String,
        rfc: String,
        password: String,
        passwordCon: String
    ) {

        if(datosCompletos(nombre, apellidoP, apellidoM, correo)){
            val preferencias = navEditarperfil.activity?.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
            var usuario = UsuarioEditar(nombre, apellidoP, apellidoM, rfc, preferencias?.getString("correoElec",""),correo, password)
            if(existePassword(password, passwordCon)){
                if(passwordCoincide(password,passwordCon)){
                    enviarDatos(navEditarperfil, preferencias!!, usuario)


                }
                else{
                    navEditarperfil.toasting("Las contraseñas no coinciden.")

                }
            }
            else{
                enviarDatos(navEditarperfil, preferencias!!, usuario)

            }
        }
        else{
            navEditarperfil.toasting("No dejes ningún campo obligatorio vacío.")
        }
    }

    private fun existePassword(password: String, passwordCon: String): Boolean {
        return !(password == "" && passwordCon == "")
    }

    private fun passwordCoincide(password: String, passwordCon: String): Boolean {
        return (password == passwordCon)
    }

    fun datosCompletos(
        nombre: String,
        apellidoP: String,
        apellidoM: String,
        correo: String
    ): Boolean {
        return !(nombre == "" || apellidoP == "" || apellidoM == "" || correo == "")
    }

    fun enviarDatos(navEditarperfil: nav_editarPerfil, preferencias: SharedPreferences, usuario: UsuarioEditar,) {
        val call = RetrofitInstance.servicioUsuarioApi.editarPerfil(usuario)
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    println(response.body().toString())
                    if(response.body().toString()=="1"){//Error
                        navEditarperfil.toasting("El correo o el RFC ya están registrados.")
                    }
                    else if(response.body().toString()=="0"){ //Exitoso
                        navEditarperfil.toasting("Datos actualizados con éxito.")
                        preferencias?.edit{
                            putString("nombre",usuario.nombre)
                            putString("apellidoP",usuario.apellidoP)
                            putString("apellidoM",usuario.apellidoM)
                            putString("correoElec",usuario.correoElecNuevo)
                            putString("rfc",usuario.rfc)
                            commit()
                        }
                        cambiarBarra(navEditarperfil)
                        navEditarperfil.findNavController().navigateUp()
                    }


                } else {
                    println("Registro fallido")
                    println(response.body().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                navEditarperfil.toasting("Verifica tu conexión a Internet.")
                if(t.cause.toString() == "java.net.ConnectException: Network is unreachable"){
                    navEditarperfil.toasting("Verifica tu conexión a Internet.")
                }
                else{
                    navEditarperfil.toasting("El servidor no está disponible.")
                }
            }
        })
    }

    fun cambiarBarra(navEditarperfil: nav_editarPerfil) {
        println("CAMBIANDO BARRA")
        var tvCorreo: TextView = navEditarperfil.requireActivity().findViewById(R.id.tv_correoMenu)
        var tvNombre: TextView = navEditarperfil.requireActivity().findViewById(R.id.tv_nombreMenu)
        var menu: NavigationMenuItemView = navEditarperfil.requireActivity().findViewById(R.id.nav_admin)
        val preferencias = navEditarperfil.activity?.getSharedPreferences("datosUsuario",AppCompatActivity.MODE_PRIVATE)
        var name = "${preferencias?.getString("nombre", "")} ${preferencias?.getString("apellidoP","")} ${preferencias?.getString("apellidoM", "")}"
        var correo = "${preferencias?.getString("correoElec", "")}"
        tvNombre.text = name
        tvCorreo.text = correo
        var isAdmin = preferencias?.getBoolean("isAdmin", false)
        println("YO SOY ADMIN? $isAdmin")
        if (isAdmin == false) {
            println("NO SOY ADMIN :(")
            menu.visibility = View.INVISIBLE
        }
    }

}