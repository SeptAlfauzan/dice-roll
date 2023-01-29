package com.example.diceroll

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.diceroll.ui.theme.DIceRollTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DIceRollTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainLayout()
                }
            }
        }
    }
}

@Composable
fun MainLayout() {
    var result: Int by remember {// Use remember composable to store objects in a Composition to memory.
        mutableStateOf(1)
    }
    var isPlay: Boolean by remember {
        mutableStateOf(false)
    }
    var isHide: Boolean by remember {
        mutableStateOf(false)
    }
    val animProgress by animateFloatAsState(
        targetValue = if(isPlay) 0.6f else 0f,
        animationSpec = tween(1500),
        finishedListener = {
            result = (1..6).random()
            isPlay = false
            isHide = true
        }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.weight(5f)) {
            this@Column.AnimatedVisibility(visible = !isHide, exit = fadeOut()) {
                LottieAnim(animProgress)
            }
            this@Column.AnimatedVisibility(visible = isHide, exit = fadeOut()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text("The result is 🎲", style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 24.sp
                    ))
                    Text(
                        "$result", style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 120.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
        Column(
            Modifier
                .weight(2f)
                .fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                isHide = false
                isPlay = true
            }) {
                Text("Roll")
            }
        }
    }
}

@Composable
fun LottieAnim(progress: Float){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.roll_dice))
//    val progress by animateLottieCompositionAsState(
//        composition = composition,
//        isPlaying = isPlaying,
//        speed = 1f,
//        clipSpec = LottieClipSpec.Progress(0f, 0.75f)
//    )
    LottieAnimation(composition = composition, progress = progress)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DIceRollTheme {
        MainLayout()
    }
}