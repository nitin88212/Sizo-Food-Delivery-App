package com.example.sizo.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminScreen(viewModel: RestaurantViewModel) {

    var orderId by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(20.dp)) {

        Text("Admin Panel 🧑‍💻", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = orderId,
            onValueChange = { orderId = it },
            label = { Text("Order ID") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Status (Preparing / Out / Delivered)") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            Log.d("ADMIN_TEST", "Button Clicked 🚀")

            viewModel.updateOrderStatus(orderId, status)
        }) {
            Text("Update Status 🚀")
        }
    }
}