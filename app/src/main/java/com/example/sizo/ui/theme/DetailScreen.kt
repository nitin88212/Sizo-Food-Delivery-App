package com.example.sizo.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(name: String, navController: NavController, viewModel: RestaurantViewModel) {
    val restaurants by viewModel.restaurants.collectAsState()

    val context = LocalContext.current

    var status by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance()
            .collection("orders")
            .document(name)
            .addSnapshotListener { snapshot, _ ->
                status = snapshot?.getString("status") ?: ""
            }
    }
    Text("Status: $status")

    val db = FirebaseFirestore.getInstance()

    db.collection("orders")
        .document(orderId)
        .addSnapshotListener { snapshot, _ ->

            if (snapshot != null && snapshot.exists()) {

                val location = snapshot.get("location") as? Map<String, Double>

                val lat = location?.get("lat") ?: 0.0
                val lng = location?.get("lng") ?: 0.0

                // 👉 update map marker
            }
        }
}


