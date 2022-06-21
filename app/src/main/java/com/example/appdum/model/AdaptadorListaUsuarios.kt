package com.example.appdum.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appdum.R
import com.example.appdum.view.RenglonListener
import java.util.*
import kotlin.collections.ArrayList

open class AdaptadorListaUsuarios (var arrUsuarios: ArrayList<UsuarioCompleto>) :
    RecyclerView.Adapter<AdaptadorListaUsuarios.UsuarioViewHolder>(), Filterable{

    var listener: RenglonListener? = null
    var usuariosFilterList = arrUsuarios
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.renglon_usuario, parent,false)
        return UsuarioViewHolder(vista)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.set(usuariosFilterList[position])
        val vista = holder.itemView
        val layoutRenglon = vista.findViewById<LinearLayout>(R.id.layoutRenglon)
        layoutRenglon.setOnClickListener {
            if(listener != null){
                listener?.clickEnRenglon(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return usuariosFilterList.size
    }

    fun actualizar(lista: List<UsuarioCompleto>?){
        usuariosFilterList.clear()
        if(lista != null){
            usuariosFilterList.addAll(lista)
        }
        notifyDataSetChanged()
    }


    class UsuarioViewHolder(vista : View) : RecyclerView.ViewHolder(vista){
        private val tvNombre = vista.findViewById<TextView>(R.id.tvNombreUsuario)
        private val tvCorreo = vista.findViewById<TextView>(R.id.tvCorreoElectronicoUsuario)
        fun set(usuarioCompleto: UsuarioCompleto) {
            tvNombre.text = "${usuarioCompleto.nombre} ${usuarioCompleto.apellidoP} ${usuarioCompleto.apellidoM}"
            tvCorreo.text = usuarioCompleto.correoElec
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0?.toString() ?: ""
                if(charString.isEmpty()){
                    usuariosFilterList = arrUsuarios
                }
                else{
                    val resultList = ArrayList<UsuarioCompleto>()
                    arrUsuarios
                        .filter {
                            (it.correoElec!!.contains(p0!!))
                        }
                        .forEach {
                            resultList.add(it)
                        }
                    usuariosFilterList = resultList
                }
                println("LISTA FILTRADA: $usuariosFilterList")
                return FilterResults().apply { values = usuariosFilterList }
            }

            @Suppress("UNCHUCKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                usuariosFilterList = if(p1?.values == null)
                    ArrayList()
                else
                    p1.values as ArrayList<UsuarioCompleto>
                notifyDataSetChanged()
            }
        }
    }
}
