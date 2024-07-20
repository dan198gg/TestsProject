package com.example.testsproject

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.testsproject.ui.theme.TestsProjectTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val fs=Firebase.firestore
            fs.collection("Users").document().set{
                User("daniil","12345")
            }
            val corScope= rememberCoroutineScope()
            var int by rememberSaveable {
                mutableStateOf(false)
            }
            val intent=Intent(this,RegisterActivity::class.java)
//            Button(onClick = { val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)}) {
//                Text(text = "TEST")
//            }
            Column(modifier = Modifier.fillMaxSize()){
                animStickman(0.8f)
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    LoadingAnim()
                    LaunchedEffect(key1="") {
                        corScope.launch {
                            delay(3000)
                            int=!int
                        }
                    }
                    if (int){
                        startActivity(intent)
                    }
                }
            }
        }
    }
}


@Composable
fun animStickman(size:Float){
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(R.drawable.animationtests, imageLoader),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(size)

    )
}


@Composable
fun LoadingAnim(maxDot: Int =3) {
    val corScope = rememberCoroutineScope()
    val context= LocalContext.current
    var dots = rememberSaveable {
        mutableIntStateOf(1)
    }
    LaunchedEffect(key1 = dots.intValue) {
        corScope.launch {
            delay(500)
            dots.intValue += 1

            if (dots.intValue == maxDot+1) {
                dots.intValue = 0
            }
        }
    }
    Row(modifier = Modifier) {
        Text(text = "Loading")
        repeat(dots.intValue) {
            Text(text = ".")
            Log.i("DOT", dots.intValue.toString())
        }

    }


}
