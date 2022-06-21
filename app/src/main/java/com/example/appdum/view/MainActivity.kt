package com.example.appdum.view

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.appdum.R
import com.example.appdum.databinding.ActivityMainBinding
import com.google.android.material.internal.NavigationMenuItemView
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import com.paypal.checkout.paymentbutton.PayPalButton
import com.paypal.pyplcheckout.BuildConfig

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val preferencias = this.getSharedPreferences("datosUsuario", MODE_PRIVATE)
        var isAdmin = preferencias.getBoolean("isAdmin", false)
        if(isAdmin){
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_inicio, R.id.nav_perfil, R.id.nav_donacion, R.id.nav_contacto, R.id.nav_cerrar_sesion, R.id.nav_admin,
                ), drawerLayout
            )
        }
        else{
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_inicio, R.id.nav_perfil, R.id.nav_donacion, R.id.nav_contacto, R.id.nav_cerrar_sesion,
                ), drawerLayout
            )
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main, menu)
        cambiarTextoLateral()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun getApp(): Application {
        return this.application
    }


    fun cambiarTextoLateral(){
        var tvCorreo : TextView = findViewById(R.id.tv_correoMenu)
        var tvNombre : TextView = findViewById(R.id.tv_nombreMenu)
        val preferencias = this.getSharedPreferences("datosUsuario", MODE_PRIVATE)
        var name = "${preferencias.getString("nombre","")} ${preferencias.getString("apellidoP","")} ${preferencias.getString("apellidoM","")}"
        var correo = "${preferencias.getString("correoElec","")}"
        tvNombre.text = name
        tvCorreo.text = correo
        var isAdmin = preferencias.getBoolean("isAdmin", false)
    }

}