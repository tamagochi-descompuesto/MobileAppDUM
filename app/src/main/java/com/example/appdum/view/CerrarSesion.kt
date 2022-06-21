package com.example.appdum.view

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appdum.viewmodel.CerrarSesionViewModel
import com.example.appdum.R

class cerrarSesion : Fragment() {

    companion object {
        fun newInstance() = cerrarSesion()
    }

    private lateinit var viewModel: CerrarSesionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val preferencias = this.activity?.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
        preferencias?.edit()?.clear()?.commit()
        val intPrincipal = Intent(this.activity, LoginActivity::class.java)
        this.startActivity(intPrincipal)
        this.activity?.finish()
        return inflater.inflate(R.layout.cerrar_sesion_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CerrarSesionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}