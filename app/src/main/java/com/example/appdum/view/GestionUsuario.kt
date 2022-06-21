package com.example.appdum.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdum.viewmodel.GestionUsuarioViewModel
import com.example.appdum.databinding.GestionUsuarioFragmentBinding
import com.example.appdum.model.AdaptadorListaDonaciones
import com.example.appdum.model.AdaptadorListaUsuarios
import com.example.appdum.model.UsuarioCorreo

class GestionUsuario : Fragment(), RenglonListener {
    private val args: GestionUsuarioArgs by navArgs()
    private val viewModel: GestionUsuarioViewModel by viewModels()
    private lateinit var binding: GestionUsuarioFragmentBinding
    private val adaptadorListaUDonaciones = AdaptadorListaDonaciones(arrayListOf())

    companion object {
        fun newInstance() = GestionUsuario()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GestionUsuarioFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarVista()
        configurarObservadores()
        adaptadorListaUDonaciones.listener = this
        viewModel.descargarUsuario(args.usuario.correoElec!!)
        viewModel.descargarDonaciones(args.usuario.correoElec!!)
        configurarRecycleView()
    }

    private fun configurarObservadores() {
        viewModel.nombre.observe(viewLifecycleOwner) {
            binding.tvNombreGestion.text = it
        }
        viewModel.enabled.observe(viewLifecycleOwner) {
            if (it == "0") {
                binding.tvBaneadoGestion.text = "Usuario con acceso a la plataforma."
            } else {
                binding.tvBaneadoGestion.text = "Usuario bloqueado de la plataforma."
                binding.switchBan.setText("Desbloquear")
                binding.switchBan.isChecked = true
            }

        }
        viewModel.isAdmin.observe(viewLifecycleOwner) {
            if (it == "0") {
                binding.tvEsAdminGestion.text = "Este usuario es administrador."
                binding.switchAdmin.setText("Degradar")
                binding.switchAdmin.isChecked = true
            } else {
                binding.tvEsAdminGestion.text = "Este usuario no es administrador."
            }
        }
        viewModel.fechaRegistro.observe(viewLifecycleOwner) {
            binding.tvFechaRegistroGestion.text = it
        }
        viewModel.rfc.observe(viewLifecycleOwner) {
            binding.tvRFCGestion.text = it
        }

        binding.switchBan.setOnClickListener {
            if (binding.switchBan.isChecked) {
                viewModel.ban(UsuarioCorreo(binding.tvCorreoGestion.text.toString()), this, binding)
            } else {
                viewModel.unban(
                    UsuarioCorreo(binding.tvCorreoGestion.text.toString()),
                    this,
                    binding
                )
            }
        }
        binding.switchAdmin.setOnClickListener {
            if (binding.switchAdmin.isChecked) {
                viewModel.darAdmin(
                    UsuarioCorreo(binding.tvCorreoGestion.text.toString()),
                    this,
                    binding
                )
            } else {
                viewModel.quitarAdmin(
                    UsuarioCorreo(binding.tvCorreoGestion.text.toString()),
                    this,
                    binding
                )
            }
        }

        viewModel.arrDonaciones.observe(viewLifecycleOwner){
                lista ->
            adaptadorListaUDonaciones.actualizar(lista)
        }
    }

    private fun configurarVista() {
        binding.tvCorreoGestion.text = args.usuario.correoElec
    }

    fun toasting(message: String) {
        Toast.makeText(this.context, "$message", Toast.LENGTH_SHORT).show()
    }

    private fun configurarRecycleView() {
        binding.rvDonaciones.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adaptadorListaUDonaciones
        }
    }

    override fun clickEnRenglon(position: Int) {
        val donacion = adaptadorListaUDonaciones.arrDonaciones[position]
        val accion = GestionUsuarioDirections.actionGestionUsuarioToGestionDonacion2(donacion)
        findNavController().navigate(accion)
    }
}
