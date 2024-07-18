package com.example.movieapp.movieList.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.NavController
import com.example.movieapp.movieList.util.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    //val authValue = authViewModel.isUserAuthenticated

    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                })
        )
        delay(3000)

//        if (authValue) {
//            navController.navigate(Screens.SearchScreen.route) {
//                popUpTo(Screens.SplashScreen.route) {
//                    inclusive = true
//                }
//            }
//        } else {
            navController.navigate(Screens.LoginScreen.route) {
                popUpTo(Screens.SplashScreen.route) {
                    inclusive = true
                }
            }

    }
}