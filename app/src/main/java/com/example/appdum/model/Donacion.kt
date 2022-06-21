package com.example.appdum.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Donacion(
    @SerializedName("idDonativo")
    var idDonativo: String? = null,
    @SerializedName("monto")
    var monto: String? = null,
    @SerializedName("fecha")
    var fecha: String? = null,
    @SerializedName("facturado")
    var facturado: String? = null,
    @SerializedName("UsuarioCorreo")
    var UsuarioCorreo : String? = null
) : Serializable

data class idDonativo(
    @SerializedName("idDonativo")
    var idDonativo: String? = null
): Serializable