package com.example.sizo.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sizo.payment.startPayment
import com.example.sizo.payment.startUPIPayment


@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: RestaurantViewModel) {

    val cartItems by viewModel.cart.collectAsState()
    val context = LocalContext.current as Activity


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Cart 🛒") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems) { item ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {

                        Row(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            // 🖼️ IMAGE
                            Image(
                                painter = painterResource(id = item.restaurant.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    item.restaurant.name,
                                    fontSize = 18.sp
                                )

                                Text("₹${item.restaurant.priceRange}")

                                Spacer(modifier = Modifier.height(8.dp))

                                // ➕➖ QUANTITY
                                Row {

                                    Button(onClick = {
                                        viewModel.decreaseQuantity(item)
                                    }) {
                                        Text("➖")
                                    }

                                    Text(
                                        "${item.quantity}",
                                        modifier = Modifier.padding(8.dp)
                                    )

                                    Button(onClick = {
                                        viewModel.increaseQuantity(item)
                                    }) {
                                        Text("➕")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 💰 TOTAL SECTION
            Text(
                "Total: ₹${viewModel.getTotal()}",
                fontSize = 22.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            // 🧾 CHECKOUT BUTTON
            Button(
                onClick = {
                    startPayment(context, viewModel.getTotal())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Pay Now 💳")
            }
            val context = LocalContext.current as Activity

            Button(
                onClick = {
                    startUPIPayment(context, viewModel.getTotal())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pay via UPI 📲")
            }
        }
    }
}