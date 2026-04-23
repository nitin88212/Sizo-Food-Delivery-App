package com.example.sizo.payment


import android.app.Activity
import com.razorpay.Checkout
import org.json.JSONObject

fun startPayment(activity: Activity, amount: Int) {
    val checkout = Checkout()
    checkout.setKeyID("rzp_test_ScDYiWixubjnu4") //  ENTER YOUR KEY ID HERE


    try {
        val options = JSONObject()
        options.put("name", "Food App")
        options.put("description", "Order Payment")
        options.put("currency", "INR")
        options.put("amount", amount * 100) // paise me convert
        options.put("method", JSONObject().apply {
            put("upi", true)
        })

        val prefill = JSONObject()
        prefill.put("email", "test@razorpay.com")
        prefill.put("contact", "9999999999")

        options.put("prefill", prefill)



        checkout.open(activity, options)

    } catch (e: Exception) {
        e.printStackTrace()
    }
}