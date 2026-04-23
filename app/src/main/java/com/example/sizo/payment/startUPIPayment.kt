package com.example.sizo.payment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

fun startUPIPayment(activity: Activity, amount: Int) {

    val uri = "upi://pay?pa=8874248693@ptyes@upi&pn=FoodApp&tn=Food Order&am=$amount&cu=INR".toUri()

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = uri

    activity.startActivity(Intent.createChooser(intent, "Pay with UPI"))
}