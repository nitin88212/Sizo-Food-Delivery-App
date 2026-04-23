package com.example.sizo.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(navController: NavController, viewModel: RestaurantViewModel) {

    val orders by viewModel.orders.collectAsState()

    LaunchedEffect(Unit) {

        FirebaseFirestore.getInstance()
            .collection("orders")
            .addSnapshotListener { snapshot, _ ->

                val orders = snapshot?.documents?.map {
                    it.data
                }

                Log.d("REALTIME", "Orders updated: $orders")
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Order History 📦") })
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
        ) {

            items(orders) { order ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Column(modifier = Modifier.padding(12.dp)) {

                        Text("Total: ₹${order.total}", fontSize = 18.sp)

                        Spacer(modifier = Modifier.height(6.dp))

                        // 🔥 STATUS TEXT
                        Text(
                            "Status: ${order.status}",
                            color = when (order.status) {
                                "Delivered" -> Color.Green
                                "Out for Delivery" -> Color.Blue
                                "Preparing" -> Color(0xFFFF9800)
                                else -> Color.Gray
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // 🔥 PROGRESS BAR
                        LinearProgressIndicator(
                            progress = when (order.status) {
                                "Placed" -> 0.25f
                                "Preparing" -> 0.5f
                                "Out for Delivery" -> 0.75f
                                "Delivered" -> 1f
                                else -> 0f
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // 🔥 ITEMS
                        order.items.forEach { item ->
                            val name = item["name"] as? String ?: ""
                            val quantity = (item["quantity"] as? Long)?.toInt() ?: 0

                            Text("$name x$quantity")
                        }
                    }
                }
            }
        }
    }


}
