package com.example.movieapp.movieList.presentation

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movieapp.R
import com.example.movieapp.movieList.util.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, viewModel: AuthenticationViewModel = hiltViewModel()) {

    val authValue by viewModel.isUserAuthenticated.collectAsState()

    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url("https://lottie.host/7b919bcd-15c2-4360-98fb-97ded08c9f28/QY6HPNAWcp.json"))

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                })
        )
        delay(1000)
        Log.e("authValue", authValue.toString())
        if (authValue) {
            navController.navigate(Screens.SearchScreen.route) {
                popUpTo(Screens.SplashScreen.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Screens.LoginScreen.route) {
                popUpTo(Screens.SplashScreen.route) {
                    inclusive = true
                }
            }

        }
    }
}