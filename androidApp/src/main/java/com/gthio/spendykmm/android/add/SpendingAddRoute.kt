package com.gthio.spendykmm.android.add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SpendingAddRoute(onBackRequest: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Box(modifier = Modifier.padding(padding), contentAlignment = Alignment.Center) {
            Button(onClick = onBackRequest) {
                Text(text = "Go Back")
            }
        }
    }
}