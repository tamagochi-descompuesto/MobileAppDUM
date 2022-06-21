package com.example.appdum.ui.Perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appdum.databinding.FragmentPerfilBinding

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentPerfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object{
        fun newInstance() = GalleryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val preferencias = this.activity?.getSharedPreferences("datosUsuario", AppCompatActivity.MODE_PRIVATE)
        var name = "${preferencias?.getString("nombre","")} ${preferencias?.getString("apellidoP","")} ${preferencias?.getString("apellidoM","")}"
        binding.tvNombreUsuario.text = name
        binding.tvCorreoUsuario.text = preferencias?.getString("correoElec","")
        binding.tvRfcUsuario.text =  preferencias?.getString("rfc","")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnEditarPerfil.setOnClickListener{
            //val accion = ListaPaisesFragDirections.actionListaPaisesFragToPaisFrag(pais)
            val accion = GalleryFragmentDirections.actionNavPerfilToNavEditarPerfil()
            findNavController().navigate(accion)
        }
    }


}