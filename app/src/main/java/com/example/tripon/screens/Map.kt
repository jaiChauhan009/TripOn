package com.example.tripon.screens

import android.content.Intent
import android.net.Uri
import android.content.ActivityNotFoundException
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OpenGoogleMaps(navController: NavController) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {

    }

    Column {
        Button(
            onClick = {
                // Create intent to start Google Maps app
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="))
                intent.setPackage("com.google.android.apps.maps")

                try {
                    launcher.launch(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Google Maps app not found", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Open Google Maps")
        }
    }
}

