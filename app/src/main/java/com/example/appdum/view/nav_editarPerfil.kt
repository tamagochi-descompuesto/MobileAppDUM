package com.example.appdum.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appdum.viewmodel.NavEditarPerfilViewModel
import com.example.appdum.databinding.NavEditarPerfilFragmentBinding
import java.util.regex.Pattern

class nav_editarPerfil : Fragment() {
    private lateinit var viewModel: NavEditarPerfilViewModel
    private lateinit var binding : NavEditarPerfilFragmentBinding

    companion object {
        fun newInstance() = nav_editarPerfil()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NavEditarPerfilViewModel::class.java)
        binding = NavEditarPerfilFragmentBinding.inflate(layoutInflater)
        var nombre = binding.tiNombreEP
        var apellidoP = binding.tiApellidoPEP
        var apellidoM = binding.tiApellidoMEP
        var correo = binding.tiCorreoElecEP
        var rfc = binding.tiRfcEP
        var password = binding.tiPasswordEP
        var passwordConf = binding.tiPasswordConfirmEP
        val preferencias = this.activity?.getSharedPreferences("datosUsuario", AppCompatActivity.MODE_PRIVATE)
        nombre.setText(preferencias?.getString("nombre",""))
        apellidoP.setText(preferencias?.getString("apellidoP",""))
        apellidoM.setText(preferencias?.getString("apellidoM",""))
        correo.setText(preferencias?.getString("correoElec",""))
        println("")
        if(preferencias?.getString("rfc","") == "NULL"){
            rfc.setText("")
        }
        else{
            rfc.setText(preferencias?.getString("rfc",""))
        }


        binding.btnEditarPerfil.setOnClickListener{
            var rfcP : Pattern = Pattern.compile("[A-Z][A-Z][A-Z][A-Z]?[0-9][0-9][0-9][0-9][0-9][0-9][a-zA-Z_0-9][a-zA-Z_0-9][a-zA-Z_0-9]")
            var correoP : Pattern = android.util.Patterns.EMAIL_ADDRESS
            var rfcMatches = rfcP.matcher(rfc.text).matches()
            var correoMatches = correoP.matcher(correo.text).matches()
            if(correoMatches && rfc.text.isNullOrEmpty()){
                viewModel.guardar(this, nombre.text.toString(), apellidoP.text.toString(),apellidoM.text.toString(),correo.text.toString(),rfc.text.toString(), password.text.toString(), passwordConf.text.toString())
            }
            else if(correoMatches && rfcMatches){
                viewModel.guardar(this, nombre.text.toString(), apellidoP.text.toString(),apellidoM.text.toString(),correo.text.toString(),rfc.text.toString(), password.text.toString(), passwordConf.text.toString())
            }
            else{
                if(correoMatches && rfc.text?.length!! != 0 && !rfcMatches){
                    toasting("El RFC es inválido")
                }
                else if(!correoMatches && rfcMatches){
                    toasting("El correo electrónico es inválido")
                }
            }
        }

        viewModel.cambiarBarra(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NavEditarPerfilViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun toasting(message : String){
        Toast.makeText(this.context,"$message", Toast.LENGTH_SHORT).show()
    }
}