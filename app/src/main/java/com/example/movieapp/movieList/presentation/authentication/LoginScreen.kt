package com.example.movieapp.movieList.presentation.authentication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.presentation.Toast
import com.example.movieapp.movieList.util.Resource
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthenticationViewModel) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emailState = remember { mutableStateOf("") }
            val passwordState = remember { mutableStateOf("") }
            Text(
                text = "Sıgn In",
                modifier = Modifier.padding(10.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
            TextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Email") },
                modifier = Modifier.padding(10.dp)
            )
            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text("Password") },
                modifier = Modifier.padding(10.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    viewModel.signIn(email = emailState.value, password = passwordState.value)
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Sign In")
                when (val response = viewModel.signInState.value) {
                    is Resource.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is Resource.Success -> {
                        Log.e("TAG", "LoginScreen: ${response.data}")
                        if (response.data!!) {
                            navController.navigate(Screens.SearchScreen.route) {
                                popUpTo(Screens.LoginScreen.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            Toast(message = "Sign In Failed")
                        }
                    }

                    is Resource.Error -> {
                        Toast(message = response.message.toString())
                    }
                }
            }
            Text(text = "New User? Sign Up", color = Color.Blue, modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navController.navigate(Screens.SignUpScreen.route) {
                        launchSingleTop = true
                    }
                })
        }

    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    val authViewModel: AuthenticationViewModel = hiltViewModel()
    LoginScreen(navController = navController, viewModel = authViewModel)
}