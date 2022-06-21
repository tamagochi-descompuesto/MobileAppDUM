package com.example.appdum.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.appdum.viewmodel.GestionDonacionViewModel
import com.example.appdum.databinding.GestionDonacionFragmentBinding
import com.example.appdum.model.AdaptadorListaDonaciones

class GestionDonacion : Fragment() {

    companion object {
        fun newInstance() = GestionDonacion()
    }
    private val args: GestionDonacionArgs by navArgs()
    private lateinit var binding: GestionDonacionFragmentBinding
    private  val viewModel: GestionDonacionViewModel by viewModels()
    private val adaptadorListaUDonaciones = AdaptadorListaDonaciones(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GestionDonacionFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configurarObservadores()
        viewModel.descargarDonaciones(args.donacion.idDonativo!!)

    }

    private fun configurarObservadores() {
        viewModel.donacion.observe(viewLifecycleOwner){
            binding.tvFechaDonacion.text = viewModel.donacion.value?.fecha
            binding.tvIdDonacion.text = viewModel.donacion.value?.idDonativo
            binding.tvMontoDonacion.text = "$${viewModel.donacion.value?.monto}"
            if(it.facturado.toString() == "1"){
                binding.tvFacturadoDonacion.text = "Esta donación aún no ha sido facturada."
                binding.switchFactura.setText("Marcar como facturada")
            }
            else{
                binding.tvFacturadoDonacion.text = "Esta donación ya ha sido facturada."
                binding.switchFactura.setText("Desmarcar como facturada")
                binding.switchFactura.isChecked = true
            }
        }

        binding.switchFactura.setOnClickListener {
            if(binding.switchFactura.isChecked){
                viewModel.marcarFactura(this, binding.tvIdDonacion.text.toString(), binding)
            }
            else{
                viewModel.desMarcarFactura(this,binding.tvIdDonacion.text.toString(), binding)
            }
        }
    }

    fun toasting(message: String) {
        Toast.makeText(this.context, "$message", Toast.LENGTH_SHORT).show()
    }

}