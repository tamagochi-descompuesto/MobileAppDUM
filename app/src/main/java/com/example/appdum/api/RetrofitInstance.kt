package com.example.appdum.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.security.cert.CertificateException

object RetrofitInstance {
    private val retrofit: Retrofit by lazy {

        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081")
            .addConverterFactory(ScalarsConverterFactory.create())      // String. Int, etc
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val servicioUsuarioApi: servidorWeb by lazy {
        retrofit.create(servidorWeb::class.java)
    }
}