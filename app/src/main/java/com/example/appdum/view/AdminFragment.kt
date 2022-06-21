package com.example.appdum.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdum.databinding.AdminFragmentBinding
import com.example.appdum.model.AdaptadorListaUsuarios
import com.example.appdum.viewmodel.AdminViewModel
import okhttp3.internal.wait

class AdminFragment : Fragment(), RenglonListener {

    companion object {
        fun newInstance() = AdminFragment()
    }

    private val viewModel: AdminViewModel by viewModels()
    private lateinit var _binding : AdminFragmentBinding
    private lateinit var searchBar : SearchView
    private val adaptadorListaUsuarios = AdaptadorListaUsuarios(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentBinding.inflate(layoutInflater)
        searchBar = _binding.searchView
        val preferencias = this.activity?.getSharedPreferences("datosUsuario", AppCompatActivity.MODE_PRIVATE)
        var isAdmin = preferencias?.getBoolean("isAdmin", false)
        if(isAdmin == true){
            _binding.lyBarraAdmin.visibility = View.VISIBLE
            _binding.clInicioSistema.visibility = View.GONE
            _binding.rvUsuarios.visibility = View.VISIBLE
        }
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarObservadores()
        configurarEventos()

        configurarRecycleView()
    }

    private fun configurarRecycleView() {
        _binding.rvUsuarios.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adaptadorListaUsuarios
        }
    }

    private fun configurarEventos() {
        viewModel.descargarUsuarios()
        adaptadorListaUsuarios.listener = this
    }

    private fun configurarObservadores() {
        viewModel.arrUsuarios.observe(viewLifecycleOwner){
            lista ->
            adaptadorListaUsuarios.actualizar(lista)
        }
        _binding.btnEntrarAdmin.setOnClickListener {
            var passsword = _binding.tiPassword.text.toString()
            if(passsword == "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"){
                _binding.lyBarraAdmin.visibility = View.VISIBLE
                _binding.clInicioSistema.visibility = View.GONE
                _binding.rvUsuarios.visibility = View.VISIBLE
                Toast.makeText(this.context, "Contraseña correcta, bienvenidx.", Toast.LENGTH_SHORT).show()
                _binding.tiPassword.setText("")
            }else{
                Toast.makeText(this.context, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show()
                _binding.tiPassword.setText("")
            }

        }

     searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
         override fun onQueryTextSubmit(p0: String?): Boolean {
             adaptadorListaUsuarios.filter.filter(p0)
             return false
         }

         override fun onQueryTextChange(p0: String?): Boolean {
             adaptadorListaUsuarios.filter.filter(p0)
             return false
         }
     })
    }



    override fun clickEnRenglon(position: Int) {
        val usuario = adaptadorListaUsuarios.usuariosFilterList[position]
        val accion = AdminFragmentDirections.actionNavAdminToGestionUsuario(usuario)
        findNavController().navigate(accion)
    }

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        // TODO: Use the ViewModel
  //  }

}