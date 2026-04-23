package com.example.sizo.ui.theme


import androidx.lifecycle.ViewModel
import com.example.sizo.DataModel.CartItem
import com.example.sizo.DataModel.Order
import com.example.sizo.DataModel.Restaurant
import com.example.sizo.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import com.google.firebase.auth.*
import android.app.Activity
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.concurrent.TimeUnit


class RestaurantViewModel : ViewModel() {

    private val db = Firebase.firestore

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    // ✅ ADD THIS (missing part)
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart


    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders


    fun loadRestaurants() {
        _restaurants.value = listOf(
            Restaurant("Sharma Cafe", "Fast Food", 200, R.drawable.first),
            Restaurant("Pizza Hub", "Italian", 400, R.drawable.second),
            Restaurant("Biryani House", "Indian", 300, R.drawable.third)
        )
    }

    fun addToCart(item: Restaurant) {
        val current = _cart.value.toMutableList()

        val existing = current.find { it.restaurant.name == item.name }

        if (existing != null) {
            existing.quantity++
        } else {
            current.add(CartItem(item))
        }

        _cart.value = current
    }

    fun increaseQuantity(item: CartItem) {
        val updated = _cart.value.toMutableList()
        updated.find { it.restaurant.name == item.restaurant.name }?.quantity++
        _cart.value = updated
    }

    fun decreaseQuantity(item: CartItem) {
        val updated = _cart.value.toMutableList()
        val existing = updated.find { it.restaurant.name == item.restaurant.name }

        if (existing != null) {
            if (existing.quantity > 1) {
                existing.quantity--
            } else {
                updated.remove(existing)
            }
        }

        _cart.value = updated
    }

    fun getTotal(): Int {
        return _cart.value.sumOf {
            it.restaurant.priceRange * it.quantity
        }
    }


    fun loadOrders() {
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->

                val orderList = result.map {
                    Order(
                        items = it["items"] as List<Map<String, Any>>,
                        total = (it["total"] as Long).toInt(),
                        timestamp = it["timestamp"] as Long,
                        status = it["status"] as? String ?: "Placed"
                    )
                }

                _orders.value = orderList
            }
    }

    fun saveOrder() {
        val order = hashMapOf(
            "items" to _cart.value.map {
                mapOf(
                    "name" to it.restaurant.name,
                    "price" to it.restaurant.priceRange,
                    "quantity" to it.quantity
                )
            },
            "total" to getTotal(),
            "timestamp" to System.currentTimeMillis(),
            "status" to "Placed"
        )

        db.collection("orders")
            .add(order)
            .addOnSuccessListener {
                _cart.value = emptyList()   // 🧹 clear cart
            }
            .addOnFailureListener {
                Log.e("ORDER", "Error saving order")
            }
    }

    fun updateStatus(order: Order, newStatus: String) {

        db.collection("orders")
            .whereEqualTo("timestamp", order.timestamp)
            .get()
            .addOnSuccessListener { result ->

                for (doc in result) {
                    db.collection("orders")
                        .document(doc.id)
                        .update("status", newStatus)
                }
            }
    }

    fun updateDeliveryLocation(orderId: String, lat: Double, lng: Double) {
        if (orderId.isBlank()) return

        Firebase.firestore.collection("delivery_location")
            .document(orderId)
            .set(
                mapOf(
                    "lat" to lat,
                    "lng" to lng
                )
            )
    }

//    fun updateStatus(orderId: String, newStatus: String) {
//
//        db.collection("orders")
//            .document(orderId)
//            .update("status", newStatus)
//    }



    fun updateOrderStatus(orderId: String, status: String) {

        val db = FirebaseFirestore.getInstance()

        Log.d("ADMIN_TEST", "Updating Firestore...")

        db.collection("orders")
            .document(orderId)
            .set(
                mapOf("status" to status),
                SetOptions.merge()
            )
            .addOnSuccessListener {
                Log.d("ADMIN_TEST", "Firestore Updated ✅")
            }
            .addOnFailureListener { e ->
                Log.e("ADMIN_TEST", "Error: ${e.message}")
            }
    }

}




class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    // 🔐 LOGIN FUNCTION
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Login failed ❌")
            }
    }

    // 🆕 SIGNUP FUNCTION
    fun signup(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("AUTH", "Signup success ✅")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("AUTH", "Signup failed: ${e.message}")
                onError(e.message ?: "Signup failed ❌")
            }
    }

    // 🚪 LOGOUT
    fun logout() {
        auth.signOut()
        Log.d("AUTH", "User logged out")
    }

    // 👤 CURRENT USER CHECK
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }


    fun firebaseAuthWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                Log.e("AUTH", "Google login failed")
                onError(it.message ?: "Google login failed ❌")
            }
    }


}