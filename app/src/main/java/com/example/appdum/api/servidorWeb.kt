package com.example.appdum.api
import com.example.appdum.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface servidorWeb {
    @POST("usuario/registro")
    fun enviarUsuario(@Body usuario: Usuario) : Call<String>

    @POST("usuario/iniciarSesion")
    fun iniciarSesion(@Body datos: UsuarioDatosInicio) : Call<String>

    @POST("usuario/editar")
    fun editarPerfil(@Body datos : UsuarioEditar) : Call<String>

    @POST("donativo/generarDonativo")
    fun generarDonativo(@Body datos: Donacion) : Call<String>

    @POST("usuario/enabled")
    fun enabledUser(@Body usuario: UsuarioEnabled) : Call<String>

    @GET("usuario/getUsuarios")
    fun getUsuarios() : Call<List<UsuarioCompleto>>

    @GET("usuario/getUsuario")
    fun getUsuario(@Query("correo") correo: String) : Call<UsuarioCompleto>

    @POST("usuario/banUsuario")
    fun banUsuario(@Body correo: UsuarioCorreo) : Call<String>

    @POST("usuario/unbanUsuario")
    fun unbanUsuario(@Body correo: UsuarioCorreo) : Call<String>

    @POST("usuario/adminUsuario")
    fun darAdminUsuario(@Body correo: UsuarioCorreo) : Call<String>

    @POST("usuario/unadminUsuario")
    fun quitarAdminUsuario(@Body correo: UsuarioCorreo) : Call<String>

    @GET("donativo/obtenerDonativos")
    fun getDonaciones(@Query("correo") correo: String) : Call<List<Donacion>>

    @GET("donativo/obtenerDonativo")
    fun getDonacion(@Query("idDonativo") idDonativo: String) : Call<Donacion>

    @POST("donativo/factura")
    fun facturaDonativo(@Body idDonativo: idDonativo) : Call<String>

    @POST("donativo/unFactura")
    fun unFacturaDonativo(@Body idDonativo: idDonativo) : Call<String>
}