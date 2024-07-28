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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.signInState.collectAsState()
    val context = LocalContext.current
    var showPassword by remember { mutableStateOf(value = false) }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

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

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
            OutlinedTextField(
                value = email.value.trim(),
                onValueChange = { email.value = it },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(6.dp)
                    ),
                placeholder = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = if (emailError) Color.Red else Color(0xFF0982C3),
                    unfocusedIndicatorColor = if (emailError) Color.Red else Color.Gray
                )
            )
            Text(
                text = "Password",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                fontSize = 18.sp,
                fontFamily = latoFontFamily,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = password.value.trim(),
                onValueChange = { password.value = it },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp)),
                placeholder = { Text("Password") },
                isError = passwordError,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = if (passwordError) Color.Red else Color(0xFF0982C3),
                    unfocusedIndicatorColor = if (passwordError) Color.Red else Color.Gray
                ),
                visualTransformation = if (showPassword) {

                    VisualTransformation.None

                } else {

                    PasswordVisualTransformation()

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                }
            )
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    fontSize = 16.sp,
                    fontFamily = latoFontFamily
                )
            }

            Button(
                onClick = {
                    emailError = email.value.isEmpty()
                    passwordError = password.value.isEmpty()
                    if (!emailError && !passwordError) {
                        viewModel.loginUser(email = email.value, password = password.value)
                    } else {
                        errorMessage = "Email and Password cannot be empty"
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
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Sign In",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = latoFontFamily
                    )
                }

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


        LaunchedEffect(key1 = state.isSuccess) {
            scope.launch {
                if (state.isSuccess!!.isNotEmpty()) {
                    val success = state.isSuccess
                    makeText(context, success.toString(), android.widget.Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.SearchScreen.route) {
                        popUpTo(Screens.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                    viewModel.resetLoadingState()
                }
            }
        }

        LaunchedEffect(key1 = state.isError) {
            scope.launch {
                if (state.isError.isNotEmpty()) {
                    errorMessage = state.isError
                    emailError = true
                    passwordError = true
                    viewModel.resetLoadingState()
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