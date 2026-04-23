package com.example.sizo.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun DeliveryBoyScreen(viewModel: RestaurantViewModel) {

    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val orderId = "testOrder123"

    LaunchedEffect(Unit) @androidx.annotation.RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION]) {

        while (true) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->

                    location?.let {
                        val lat = it.latitude
                        val lng = it.longitude

                        viewModel.updateDeliveryLocation(orderId, lat, lng)
                    }
                }

            delay(5000) // every 5 sec update
        }
    }

    Text("Real GPS Tracking ON 📡")

    val db = FirebaseFirestore.getInstance()

    fun updateLocation(orderId: String, lat: Double, lng: Double) {
        db.collection("orders")
            .document(orderId)
            .update(
                mapOf(
                    "location" to mapOf(
                        "lat" to lat,
                        "lng" to lng
                    )
                )
            )
    }
}