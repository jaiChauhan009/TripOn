package com.example.tripon.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tripon.AiViewModel

@Composable
fun AI(navController: NavController){
    val viewModel: AiViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    var input by remember { mutableStateOf("") }
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        OutlinedTextField(value = input ,
            label = { Text(text = "Enter your text")},
            onValueChange = {input =it})
        Button(onClick = {
            viewModel.generateStory("give your response according to visiting " +
                    "site if user ask you data apart of visiting or travelling say user" +
                    " that i am not able to answer your question, you can give url to asked" +
                    " question and give your response according to that $input")
        }) {
            Text(text = "Submit")
        }
        val textGenerationResult by viewModel.textGenerationResult.collectAsState()
        Text(text = textGenerationResult ?: "")
    }
}