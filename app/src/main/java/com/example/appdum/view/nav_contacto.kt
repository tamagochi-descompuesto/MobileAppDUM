package com.example.appdum.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.appdum.viewmodel.NavContactoViewModel
import com.example.appdum.R
import com.google.android.material.snackbar.Snackbar
import com.example.appdum.databinding.NavContactoFragmentBinding

class nav_contacto : Fragment() {
    private lateinit var binding : NavContactoFragmentBinding
    private lateinit var viewModel: NavContactoViewModel
    lateinit var btnEnviar : Button
    lateinit var asunto : String
    lateinit var mensaje : String
    lateinit var nombre : String
    lateinit var correo : String
    companion object {
        fun newInstance() = nav_contacto()
    }
    //private val CORREO_ELECTRONICO = "hernandezsilvaerick@gmail.com"
    private val CORREO_ELECTRONICO = "contacto@dibujando.org.mx"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NavContactoFragmentBinding.inflate(layoutInflater)
        val vista = binding.root
        btnEnviar = binding.btnEnviar
        val preferencias = this.activity?.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
        binding.tiCorreo.setText("${preferencias?.getString("correoElec","")}")
        binding.tiNombreEP.setText("${preferencias?.getString("nombre","")} ${preferencias?.getString("apellidoP","")} ${preferencias?.getString("apellidoM","")}")
        configurarObservadores()
        return vista
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NavContactoViewModel::class.java)
        binding.fab.setOnClickListener { view ->
            val builder = AlertDialog.Builder(this.context)
                .setTitle("Contáctanos")
                .setMessage("Teléfono: \n   55 21 22 52 86 \n\nCorreo electrónico: \n   contacto@dibujando.org.mx")
                .setPositiveButton("Cerrar"){
                        _,_->
                }
            builder.show()
        }

    }
    fun sendEmail(){
        asunto = binding.tiAsunto.text.toString()
        mensaje = binding.tiMensaje.text.toString()
        nombre = binding.tiNombreEP.text.toString()
        correo = binding.tiCorreo.text.toString()
        if(asunto.isNullOrEmpty() || mensaje.isNullOrEmpty()){
            Toast.makeText(this.activity, "Escribe un un asunto y un mensaje.", Toast.LENGTH_LONG)
                .show()
        }
        else{
            var fixed_message = "Hola, mi nombre es $nombre, escribo este correo electrónico por lo siguiente: \n\n$mensaje"
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",CORREO_ELECTRONICO,""))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"$asunto")
            emailIntent.putExtra(Intent.EXTRA_TEXT,"$fixed_message")
            binding.tiAsunto.setText("")
            binding.tiMensaje.setText("")
            startActivity(emailIntent)
        }

    }

    fun configurarObservadores(){
        btnEnviar.setOnClickListener{
            sendEmail()
        }
    }
}