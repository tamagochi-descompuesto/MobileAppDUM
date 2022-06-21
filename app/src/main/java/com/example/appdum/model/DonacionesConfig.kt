package com.example.appdum.model

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.appdum.view.MainActivity
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import com.paypal.pyplcheckout.BuildConfig

class DonacionesConfig : Application() {
    private val YOUR_CLIENT_ID: String = "ARm48m-wYm0B6_SMaK_bNRSaS3Q7tbY_iohNR3rnvERtwskJujOYCj7xwqR1UgVln1NhgjHoEXN4X_st"
    override fun onCreate() {
        super.onCreate()
        val config = CheckoutConfig(
            application = this,//Recibe Application
            clientId = YOUR_CLIENT_ID,
            environment = Environment.SANDBOX,
            returnUrl = "com.example.appdum://paypalpay",
            currencyCode = CurrencyCode.MXN,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        PayPalCheckout.setConfig(config)
    }

}