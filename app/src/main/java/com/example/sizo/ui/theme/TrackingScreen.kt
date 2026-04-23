package com.example.sizo.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TrackingScreen(orderId: String) {

    var location by remember {
        mutableStateOf(LatLng(25.545, 81.478))
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    // 🔥 Firestore se live location listen
    LaunchedEffect(Unit) {

        Firebase.firestore.collection("delivery_location")
            .document(orderId)
            .addSnapshotListener { snapshot, _ ->

                val lat = snapshot?.getDouble("lat") ?: 0.0
                val lng = snapshot?.getDouble("lng") ?: 0.0

                location = LatLng(lat, lng)
            }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {

        Marker(
            state = MarkerState(position = location),
            title = "Delivery Boy 🛵"
        )
    }
}