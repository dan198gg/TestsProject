package com.example.testsproject

import android.os.Bundle
import android.text.Layout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testsproject.ui.theme.TestsProjectTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Text(text = "Регистрация", fontSize = 50.sp, modifier = Modifier.padding(30.dp))
            UserAndPassvord()

        }
    }
}


@Composable
fun UserAndPassvord(){

    val context= LocalContext.current
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(4.dp))
    ) {
        var userLogin by remember { mutableStateOf("")}
        var userPassword by remember { mutableStateOf("")}
        TextField(value = userLogin, onValueChange ={userLogin=it}, label = { Text(text = "Введите логин")})
        TextField(value = userPassword, onValueChange ={userPassword=it}, label = { Text(text = "Введите пароль")}, modifier = Modifier.padding(0.dp,50.dp))
        Button(onClick = {
            val storage=Firebase.firestore
            storage.collection("Users").document().set{
                User(login = userLogin, password = userPassword.toInt())
            }
        }) {
            Text(text = "Зарегистрироваться")
        }
    }
}