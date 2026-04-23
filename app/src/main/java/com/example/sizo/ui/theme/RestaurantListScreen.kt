package com.example.sizo.ui.theme

import com.example.sizo.ui.theme.RestaurantCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sizo.DataModel.Restaurant
import com.example.sizo.FoodApp
import com.example.sizo.R

@Composable
fun RestaurantListScreen(
    restaurants: List<Restaurant>,
    onClick: (Restaurant) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(restaurants) { restaurant ->
            RestaurantCard(restaurant) {
                onClick(restaurant)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Text("Preview")
}