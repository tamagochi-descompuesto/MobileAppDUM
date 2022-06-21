package com.example.appdum.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import com.example.appdum.databinding.ActivityRegistroBinding
import com.example.appdum.model.Usuario
import com.google.android.material.textfield.TextInputEditText
import com.example.appdum.viewmodel.RegistroViewModel
import java.util.regex.Pattern

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private val viewModel: RegistroViewModel by viewModels()

    lateinit var nombre : TextInputEditText
    lateinit var apellidoP : TextInputEditText
    lateinit var apellidoM : TextInputEditText
    lateinit var rfc : TextInputEditText
    lateinit var correoElec : TextInputEditText
    lateinit var password : TextInputEditText
    lateinit var confirmPassword : TextInputEditText
    lateinit var checkBoxTerminos : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCrearCuenta.setOnClickListener{
            crearCuenta()
        }
        binding.tvPoliticaRE.setOnClickListener{
            redirigirPolitica()
        }
        binding.cbTermycon.setOnClickListener {
            if(binding.cbTermycon.isChecked) {
                redirigirTerminos()
            }else{
                toasting("Debes aceptar los términos y condiciones.")
            }
        }
    }

    private fun redirigirTerminos() {
        val int = Intent(this, TerminosCondiciones::class.java)
        startActivity(int)
    }

    fun crearCuenta(){
        nombre = binding.tiNombreEP
        apellidoP = binding.tiApellidoPaterno
        apellidoM = binding.tiApellidoMEP
        rfc = binding.tiRfc
        correoElec = binding.tiCorreoElec
        password = binding.tiPassword
        confirmPassword = binding.tiConfirmPassword
        checkBoxTerminos = binding.cbTermycon
        rfc.setText(rfc.text.toString().uppercase())
        var rfcP : Pattern = Pattern.compile("[A-Z][A-Z][A-Z][A-Z]?[0-9][0-9][0-9][0-9][0-9][0-9][a-zA-Z_0-9][a-zA-Z_0-9][a-zA-Z_0-9]")
        var usuario : Usuario
        //Revisamos si los términos y condiciones han sido aceptados
        if(checkBoxTerminos.isChecked){
            //Se confirma que la contraseña sea válida
            if(confirmPassword.text?.length!! >= 8 && confirmPassword.text?.length!! >= 8){
                //Se confirma que el correo sea válido
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(correoElec.text.toString()).matches()){
                    //Se confirma que las contraseñas coincidan
                    if(password.text.toString() == confirmPassword.text.toString()){
                        //Se revisa si el RFC es válido
                        if (rfc.text!!.isEmpty() || rfcP.matcher(rfc.text.toString()).matches()){
                            //Se revisa que no se dejen campos vacios y si se registra RFC o no
                            if(nombre.text.isNullOrEmpty() || apellidoP.text.isNullOrEmpty() || apellidoM.text.isNullOrEmpty()
                                || correoElec.text.isNullOrEmpty() || password.text.isNullOrEmpty() || confirmPassword.text.isNullOrEmpty()){
                                toasting("No dejes ningún campo vacío.")
                            }
                            else{
                                if(rfc.text.isNullOrEmpty()){
                                    usuario = Usuario(nombre.text.toString(),apellidoP.text.toString(),apellidoM.text.toString(),
                                        "null",correoElec.text.toString(),password.text.toString())
                                }
                                else{
                                    usuario = Usuario(nombre.text.toString(),apellidoP.text.toString(),apellidoM.text.toString(),
                                        rfc.text.toString(),correoElec.text.toString(),password.text.toString())
                                }
                                viewModel.enviarUsuario(usuario, this)

                                //println("${usuario.nombre} ${usuario.apellidoP} ${usuario.apellidoM} ${usuario.correoElec} ${usuario.password} ${usuario.rfc}")
                            }
                        }
                        else{
                            toasting("Introduce un RFC válido.")
                        }
                    }
                    else{
                        toasting("Las contraseñas no coinciden.")
                    }
                }
                else{
                    toasting("Ingresa un correo electrónico válido.")
                }
            }
            else{
                toasting("Tu contraseña debe ser tener al menos 8 caracteres de longitud.")
            }
        }
        else{
            toasting(("Debes aceptar los términos y condiciones para continuar."))
        }


        //nombre = binding.
    }

    fun toasting(message : String){
        Toast.makeText(baseContext,"$message", Toast.LENGTH_SHORT).show()
    }

    fun redirigirPolitica(){
        val url= "https://www.dibujando.org.mx/aviso-de-privacidad/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }



}