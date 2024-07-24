package com.example.movieapp.movieList.presentation.login_screen

import android.widget.Toast.makeText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.presentation.viewmodel.AuthenticationViewModel
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.latoFontFamily
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthenticationViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val state = viewModel.signInState.collectAsState(initial = null)
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState()),
        ) {
            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Login",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 45.sp,
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Text(
                text = "Email",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                fontSize = 18.sp,
                fontFamily = latoFontFamily,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = email.value.trim(),
                onValueChange = { email.value = it },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(6.dp)
                    ),
                placeholder = { Text("Email") }

            )
            Text(
                text = "Password",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                fontSize = 18.sp,
                fontFamily = latoFontFamily,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = password.value.trim(),
                onValueChange = { password.value = it },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp)),
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text("Password") }
            )
            Button(
                onClick = {
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        viewModel.loginUser(email = email.value, password = password.value)
                    } else {
                        makeText(context, "Email and Password cannot be empty", android.widget.Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF0982C3)),
                colors = ButtonDefaults.buttonColors(Color(0xFF0982C3))
            ) {
                Text(text = "Sign In", color = Color.White, fontSize = 18.sp, fontFamily = latoFontFamily)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Don’t have an account? ",
                    color = Color.White,
                    fontSize = 16.sp, fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Signup",
                    color = Color(0xFF0982C3),
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.SignUpScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }

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