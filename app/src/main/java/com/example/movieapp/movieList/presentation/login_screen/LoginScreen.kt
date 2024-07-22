package com.example.movieapp.movieList.presentation.login_screen

import android.util.Log
import android.widget.Toast.makeText
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.util.Screens
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, viewModel: SignInViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val state = viewModel.signInState.collectAsState(initial = null)
    val context = LocalContext.current
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
            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }

            Text(
                text = "Sıgn In",
                modifier = Modifier.padding(10.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                modifier = Modifier.padding(10.dp),
                placeholder = { Text("Email") }
            )
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                modifier = Modifier.padding(10.dp),
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text("Password") }
            )
            Button(
                onClick = {
                    viewModel.loginUser(email = email.value, password = password.value)
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Sign In")
            }
            Text(text = "New User? Sign Up", color = Color.Blue, modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navController.navigate(Screens.SignUpScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }


        LaunchedEffect(key1 = state.value?.isSuccess) {
            scope.launch {
                if (state.value?.isSuccess!!.isNotEmpty() == true) {
                    val success = state.value?.isSuccess
                    makeText(context, success.toString(), android.widget.Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.SearchScreen.route) {
                        popUpTo(Screens.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }

        LaunchedEffect(key1 = state.value?.isError) {
            scope.launch {
                if (state.value?.isError!!.isNotEmpty() == true) {
                    val error = state.value?.isError
                    makeText(context, error.toString(), android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}


@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}