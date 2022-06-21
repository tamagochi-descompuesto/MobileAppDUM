package com.example.appdum.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable
//import com.google.gson.annotations.SerializedName
data class Usuario(
    @SerializedName("nombre")
    var nombre: String? = null,
    @SerializedName("apellidoP")
    var apellidoP: String? = null,
    @SerializedName("apellidoM")
    var apellidoM: String? = null,
    @SerializedName("rfc")
    var rfc: String? = null,
    @SerializedName("correoElec")
    var correoElec: String? = null,
    @SerializedName("password")
    var password: String? = null
)

data class UsuarioDatosInicio(
    @SerializedName("correoElec")
    var correo: String? = null,
    @SerializedName("password")
    var password: String? = null
)

data class UsuarioRecibe(
    @SerializedName("nombre")
    var nombre: String? = null,
    @SerializedName("apellidoP")
    var apellidoP: String? = null,
    @SerializedName("apellidoM")
    var apellidoM: String? = null,
    @SerializedName("rfc")
    var rfc: String? = null,
    @SerializedName("correoElec")
    var correoElec: String? = null,
    @SerializedName("isAdmin")
    var isAdmin: String? = null
)

data class UsuarioEditar(
    @SerializedName("nombre")
    var nombre: String? = null,
    @SerializedName("apellidoP")
    var apellidoP: String? = null,
    @SerializedName("apellidoM")
    var apellidoM: String? = null,
    @SerializedName("rfc")
    var rfc: String? = null,
    @SerializedName("correoElecViejo")
    var correoElecViejo: String? = null,
    @SerializedName("correoElecNuevo")
    var correoElecNuevo: String? = null,
    @SerializedName("password")
    var password: String? = null
)

data class UsuarioEnabled(
    @SerializedName("correoElec")
    var correo: String? = null,
)


data class UsuarioCompleto(
    @SerializedName("nombre")
    var nombre: String? = null,
    @SerializedName("apellidoPaterno")
    var apellidoP: String? = null,
    @SerializedName("apellidoMaterno")
    var apellidoM: String? = null,
    @SerializedName("rfc")
    var rfc: String? = null,
    @SerializedName("correo")
    var correoElec: String? = null,
    @SerializedName("enabled")
    var enabled: String? = null,
    @SerializedName("isAdmin")
    var isAdmin: String? = null,
    @SerializedName("fechaRegistro")
    var fechaRegistro: String? = null
) : Serializable

data class UsuarioCorreo(
    @SerializedName("correo")
    var correo: String? = null
)