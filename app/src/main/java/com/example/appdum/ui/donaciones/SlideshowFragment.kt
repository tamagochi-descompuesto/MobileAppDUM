package com.example.appdum.ui.donaciones

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appdum.databinding.FragmentDonacionesBinding
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import com.paypal.checkout.paymentbutton.PayPalButton
import java.util.*
import kotlin.random.Random.Default.nextInt

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentDonacionesBinding? = null
    lateinit var payPalButton : PayPalButton
    lateinit var preferences: SharedPreferences
    lateinit var preID: String
    //TextWatcher que observa el textinputField para ver si ha cambiado el monto a donar
    private val textWatcher = object : TextWatcher{
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //Cuando el texto cambie, ponemos la nueva configuración al botón
            if(p0.isNullOrEmpty() || p0.toString().toInt() < 100){
                toasting()
                payPalButton.isVisible = false
            }
            else{
                payPalButton.isVisible = true
                var rfc= preferences.getString("rfc","")
                var nombre= preferences.getString("nombre","")
                var apellidoP = preferences.getString("apellidoP","")
                var apellidoM = preferences.getString("apellidoM","")
                var currentTime = System.currentTimeMillis()
                var correo = preferences.getString("correoElec","")
                preID = "${p0}${rfc!![rfc.length-3]}${rfc!![rfc.length-2]}${rfc!![rfc.length-1]}${currentTime}${apellidoP!![apellidoP.length-1]}${apellidoM!![apellidoM.length-1]}${nombre!![nombre.length-1]}"
                slideshowViewModel.setupPaypal(payPalButton,p0.toString(), preID, correo!!, this@SlideshowFragment)
            }
        }
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    //La variable donde almacenaremos el editText
    lateinit var input: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentDonacionesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        super.onCreate(savedInstanceState)
        //Obtenemos el paypal button
        payPalButton = binding.PayPalButton
        //Obtenemos el valor del monto a donar y lo convertimos a string
        val ti_monto = binding.tiMonto.text.toString()
        //Mandamos a llamar el método para configurar el boton en el VM
        preferences = requireActivity().getSharedPreferences("datosUsuario", MODE_PRIVATE)
        var rfc= preferences.getString("rfc","")
        var nombre= preferences.getString("nombre","")
        var apellidoP = preferences.getString("apellidoP","")
        var apellidoM = preferences.getString("apellidoM","")
        var currentTime = System.currentTimeMillis()
        var correo = preferences.getString("correoElec","")
        preID = "${ti_monto}${rfc!![rfc.length-3]}${rfc!![rfc.length-2]}${rfc!![rfc.length-1]}${apellidoP!![apellidoP.length-1]}${apellidoM!![apellidoM.length-1]}${nombre!![nombre.length-1]}"
        slideshowViewModel.setupPaypal(payPalButton,ti_monto, preID, correo!!, this)
        //Se configuran los observadores
        configurarObservadores()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun configurarObservadores(){
        //Le asociamos el textinputField a la variable
        input = binding.tiMonto
        //Añadimos el listener
        input.addTextChangedListener(textWatcher)

    }

    fun toasting(){
        Toast.makeText(this.activity, "Por favor, ingresa un monto mayor o igual a $100.", Toast.LENGTH_LONG)
            .show()
    }

    fun toasting(texto: String){
        Toast.makeText(this.activity, texto, Toast.LENGTH_LONG)
            .show()
    }


}