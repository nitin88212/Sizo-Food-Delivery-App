package com.example.sizo.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sizo.FoodApp
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: RestaurantViewModel) {
    val restaurants by viewModel.restaurants.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRestaurants()
    }



    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Hey Nitin 👋",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search food 🍔") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }

    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("orders") },
            icon = { Icon(Icons.Default.List, contentDescription = "") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = "") }
        )
    }



    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Food App 🍔") }, actions = {
                Text(
                    "Cart",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { navController.navigate("cart") }
                )

                Text(
                    "Orders",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("orders")
                        }
                )

                Text(
                    "Logout",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {

                            FirebaseAuth.getInstance().signOut()

                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                )

                Text(
                    "Profile",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("profile")
                        }
                )

                Text(
                    "Admin",
                    modifier = Modifier.clickable {
                        navController.navigate("admin")
                    }
                )

                Text(
                    "Delivery Panel",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("delivery")
                        }
                )
            })
        }
    ) { padding ->

        RestaurantListScreen(
            restaurants = restaurants,
            modifier = Modifier.padding(padding),
            onClick = { restaurant ->
                navController.navigate("details/${restaurant.name}")
            }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    FoodApp(orderId = null)
}
