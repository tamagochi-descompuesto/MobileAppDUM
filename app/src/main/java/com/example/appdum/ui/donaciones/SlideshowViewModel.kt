package com.example.appdum.ui.donaciones

import androidx.lifecycle.ViewModel
import com.example.appdum.api.RetrofitInstance
import com.example.appdum.model.Donacion
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.ErrorInfo
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.*
import com.paypal.checkout.paymentbutton.PayPalButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SlideshowViewModel : ViewModel() {

    fun setupPaypal(payPalButton: PayPalButton, monto: String, preID: String, correo: String, view: com.example.appdum.ui.donaciones.SlideshowFragment){
        payPalButton.setup(
            createOrder = CreateOrder { createOrderActions ->
                val order = Order(
                    intent = OrderIntent.CAPTURE,
                    appContext = AppContext(
                        userAction = UserAction.PAY_NOW
                    ),
                    purchaseUnitList = listOf(
                        PurchaseUnit(
                            amount = Amount(
                                currencyCode = CurrencyCode.MXN,
                                value = "$monto"
                            )
                        )
                    )
                )
                createOrderActions.create(order)
            },
            onApprove = OnApprove { approval ->
                approval.orderActions.capture { captureOrderResult ->
                    //Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                    println("AAAAAAAAAAAAAAAAAAQUI: \n ${approval.data.paymentId}  ${approval.data.orderId}  ${approval.data.payerId}")
                    println("ORDER RESULT: ${captureOrderResult.toString()}")
                    var currentTime = System.currentTimeMillis()
                    var paymentID = preID + "${currentTime}"
                    view.toasting("¡Muchas gracias por tu donativo!")
                    transactionApproved(captureOrderResult, paymentID, monto, paymentID, correo)

                    println("$captureOrderResult")
                }
            },
            onError = OnError { error ->
                transactionError(error)
                view.toasting("¡Oh, no! Ocurrió un error al procesar tu donativo.")

            }
        )
    }

    fun transactionApproved(orderResult: CaptureOrderResult, paymentID: String?, monto: String, ID:String, correo: String){
        println("CORREO: ${correo}")
        var donativo = Donacion(ID,monto,null,"1", correo)
        val call = RetrofitInstance.servicioUsuarioApi.generarDonativo(donativo)
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                println(response)
                if (response.isSuccessful) {
                    println("Registro exitoso")
                    println(response.body().toString())
                    if(response.body().toString()=="1"){//Error
                        //registroActivity.toasting("El correo o el RFC ya están registrados.")
                    }
                    else if(response.body().toString()=="0"){ //Exitoso
                        //registroActivity.toasting("Registro exitoso, puedes iniciar sesión.")
                        //registroActivity.finish()
                    }


                } else {
                    println("Registro fallido")
                    println(response.body().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                //registroActivity.toasting("Verifica tu conexión a Internet.")
                if(t.cause.toString() == "java.net.ConnectException: Network is unreachable"){
                    //registroActivity.toasting("Verifica tu conexión a Internet.")
                }
                else{
                    //registroActivity.toasting("El servidor no está disponible.")
                }
            }
        })

        println("Resultado: ${orderResult}")
        println("paymentID:${paymentID}")
    }

    fun transactionError(error: ErrorInfo) {
        println("Error:  ${error.reason}")
    }


}