package com.example.appdum.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appdum.R
import com.example.appdum.view.RenglonListener

class AdaptadorListaDonaciones(var arrDonaciones: ArrayList<Donacion>) :
    RecyclerView.Adapter<AdaptadorListaDonaciones.DonacionViewHolder>() {

    var listener: RenglonListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonacionViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.renglon_donacion, parent,false)
        return DonacionViewHolder(vista)
    }

    override fun onBindViewHolder(holder: DonacionViewHolder, position: Int) {
        holder.set(arrDonaciones[position])
        println("ESTOY CREANDO: ${arrDonaciones[position]}")
        val vista = holder.itemView
        val layoutRenglon = vista.findViewById<LinearLayout>(R.id.layoutRenglonDona)
        layoutRenglon.setOnClickListener {
            println("POSICION ${holder.layoutPosition}")
            println("Click sobre ${arrDonaciones[position]}")
            if(listener != null){
                listener?.clickEnRenglon(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrDonaciones.size
    }

    fun actualizar(lista: List<Donacion>?){
        arrDonaciones.clear()
        if(lista != null){
            arrDonaciones.addAll(lista)
            println("NUEVOS: ${arrDonaciones}")
        }
        notifyDataSetChanged()
    }


    class DonacionViewHolder(vista : View) : RecyclerView.ViewHolder(vista){
        private val tvFecha = vista.findViewById<TextView>(R.id.tvFechaDona)
        private val tvMonto = vista.findViewById<TextView>(R.id.tvMontoDona)
        private val tvFacturado = vista.findViewById<TextView>(R.id.tvFacturadoDOna)
        private val tvId = vista.findViewById<TextView>(R.id.tvIdDonativoDona)
        fun set(donacion: Donacion) {
            tvFecha.text = "Fecha: ${donacion.fecha}"
            tvMonto.text = "Monto: ${donacion.monto}"
            tvId.text = "ID: ${donacion.idDonativo}"
            if(donacion.facturado == "1"){
                tvFacturado.text = "Sin facturar"
            }else{
                tvFacturado.text = "Facturado"
            }

        }
    }
}