package com.example.sizo.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    val user = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Profile 👤") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
        ) {

            // 👤 USER INFO
            Text("User ID:", fontSize = 14.sp)
            Text(user?.uid ?: "No User", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Text("Phone:", fontSize = 14.sp)
            Text(user?.phoneNumber ?: "Not Available", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(30.dp))

            // 📦 ORDERS BUTTON
            Button(
                onClick = {
                    navController.navigate("orders")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("My Orders 📦")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🚪 LOGOUT BUTTON
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()

                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout 🚪")
            }
        }
    }
}