@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.smarthomeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Lightbulb  //
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.sp


import com.example.smarthomeapp.ui.theme.SmartHomeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()// Allows full screen layouts without default status bar padding
        setContent {
            SmartHomeAppTheme {
                SmartHomeApp()
            }// Defines the UI using JC
        }
    }
}

//Defining the App Screens
enum class Screen(val title: String) {
    Favorites("Favorites"),
    Things("Things")
}

@OptIn(ExperimentalMaterial3Api::class)
//Building the Main UI with Navigation
@Composable
fun SmartHomeApp() {
    val navController = rememberNavController()// creates a navigation controller for handling screen changes
    var currentScreen by remember { mutableStateOf(Screen.Favorites) }//keeps track of the current screen.

    // Layout that provides common UI elements
    Scaffold(
        topBar = { AppTopBar() },  // title bar
        bottomBar = { BottomNavigationBar(navController, currentScreen) },// adds bottom navigation
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle adding favorite routines */ },
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Favorite")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = Screen.Favorites.name,
            Modifier.padding(paddingValues)
        ) {
            composable(Screen.Favorites.name) {
                currentScreen = Screen.Favorites
                FavoritesScreen()
            }
            composable(Screen.Things.name) {
                currentScreen = Screen.Things
                ThingsScreen()
            }
        }
    }
}
@Composable
fun AppTopBar(){
    Column{
        CenterAlignedTopAppBar(
            title ={
                Text(text = "My Smart Home", style = MaterialTheme.typography.headlineSmall)
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Yellow)
        )
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentScreen: Screen) {
    val items = listOf(
        Screen.Favorites to Icons.Filled.Star,
        Screen.Things to Icons.Filled.Home,
        "Routines" to Icons.Filled.List,  // Added icon
        "Ideas" to Icons.Outlined.Lightbulb,  // Added icon
        "Settings" to Icons.Filled.Settings  // Added icon
    )

    NavigationBar(containerColor = Color.White) {
        items.forEach { (screen, icon) ->
            NavigationBarItem(
                selected = currentScreen.title == screen.toString(),
                onClick = {
                    if (screen is Screen) navController.navigate(screen.name)
                },
                icon = {
                    Icon(imageVector = icon, contentDescription = screen.toString())
                },
                label = { Text(screen.toString()) },
                alwaysShowLabel = true,
                enabled = screen is Screen, // Disable clicking for Routines, Ideas, and Settings
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Yellow,
                    unselectedIconColor = Color.Gray,
                    disabledIconColor = Color.LightGray // Gray out inactive buttons
                )
            )
        }
    }
}


@Composable
fun FavoritesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),  // Adjust space for FloatingActionButton
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Favorites",
            tint = Color.Gray,
            modifier = Modifier.size(80.dp) // Large icon
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No Favorites!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Add your favorite routines for easy access here.",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Tap the '+' button below to add your favorite routines.",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Composable
fun ThingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Large GridView Icon
        Icon(
            imageVector = Icons.Filled.GridView,
            contentDescription = "Things Icon",
            modifier = Modifier.size(120.dp),
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "No things!",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "It looks like we didn’t discover any devices.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Extra Message
        Text(
            text = "Try an option below",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons (Icons separated from names, Names in Blue)
        ActionButton(Icons.Filled.Search, "Run discovery")
        ActionButton(Icons.Filled.Add, "Add a cloud account")
        ActionButton(Icons.Filled.Info, "View our supported devices")
        ActionButton(Icons.Filled.Email, "Contact support")
    }
}

// Custom Action Button with Blue Text and Separated Icon
@Composable
fun ActionButton(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 32.dp)
            .clickable { /* Handle click */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .background(Color.Blue, shape = CircleShape)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    SmartHomeAppTheme {
        SmartHomeApp()
    }
}
