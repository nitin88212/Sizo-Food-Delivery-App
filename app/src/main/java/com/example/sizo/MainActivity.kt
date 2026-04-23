package com.example.sizo



import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sizo.ui.theme.AdminScreen
import com.example.sizo.ui.theme.AuthViewModel
import com.example.sizo.ui.theme.CartScreen
import com.example.sizo.ui.theme.DeliveryBoyScreen
import com.example.sizo.ui.theme.DetailScreen
import com.example.sizo.ui.theme.HomeScreen
import com.example.sizo.ui.theme.LoginScreen
import com.example.sizo.ui.theme.OrderHistoryScreen
import com.example.sizo.ui.theme.ProfileScreen
import com.example.sizo.ui.theme.RestaurantListScreen
import com.example.sizo.ui.theme.RestaurantViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.razorpay.PaymentResultListener

class MainActivity : ComponentActivity(), PaymentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderId = intent.getStringExtra("orderId")

        if (orderId != null) {
            Log.d("NAVIGATION", "Open order: $orderId")
        }

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("orders")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM_DEBUG", "Subscribed to orders ✅")
                } else {
                    Log.e("FCM_DEBUG", "Subscription failed ❌")
                }
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }


        setContent {
            FoodApp(orderId)
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successful 🎉", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed ❌", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodApp(orderId: String?) {

    val navController = rememberNavController()
    val viewModel: RestaurantViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    val currentUser = FirebaseAuth.getInstance().currentUser
    val startDestination = if (currentUser != null) "home" else "login"



    NavHost(navController, startDestination = startDestination) {

        composable("login") {
            LoginScreen(navController, authViewModel)
        }

        composable("home") {
            HomeScreen(navController, viewModel)
        }

        composable("details/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            DetailScreen(name, navController, viewModel)
        }

        composable("cart") {
            CartScreen(navController, viewModel)
        }

        composable("orders?orderId={orderId}") { backStackEntry ->

            val orderIdArg = backStackEntry.arguments?.getString("orderId")

            OrderHistoryScreen(
                navController = navController,
                viewModel = viewModel
                // later you can pass orderIdArg if needed
            )
        }
        composable("profile") {
            ProfileScreen(navController)
        }

        composable("admin") {
            AdminScreen(viewModel)
        }

        composable("delivery") {
            DeliveryBoyScreen(viewModel)
        }

    }

    LaunchedEffect(Unit) {
        viewModel.loadRestaurants()
    }





    LaunchedEffect(orderId) {
        if (orderId != null) {
            navController.navigate("orders?orderId=$orderId") {
                popUpTo("login") { inclusive = false }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodApp(orderId = null)
}



