package com.example.movieapp.movieList.presentation.signup_screen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
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
import com.example.movieapp.R
import com.example.movieapp.movieList.presentation.components.MovieButton
import com.example.movieapp.movieList.presentation.components.MovieLoginText
import com.example.movieapp.movieList.presentation.components.MovieTextField
import com.example.movieapp.movieList.presentation.viewmodel.AuthenticationViewModel
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.buttonColor
import com.example.movieapp.ui.theme.latoFontFamily
import com.example.movieapp.ui.theme.whiteColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val state by viewModel.registerState.collectAsState()
    val context = LocalContext.current
    var showPassword by remember { mutableStateOf(value = false) }
    var confirmShowPassword by remember { mutableStateOf(value = false) }
    var errorMessage by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    val emailEmptyMessage = stringResource(id = R.string.email_empty)
    val passwordEmptyMessage = stringResource(id = R.string.password_empty)
    val confirmPasswordEmptyMessage = stringResource(id = R.string.confirm_password_empty)
    val passwordsNotMatchMessage = stringResource(id = R.string.passwords_not_match)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
            val confirmPassword = remember { mutableStateOf("") }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    modifier = Modifier.padding(10.dp),
                    fontSize = 45.sp,
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            MovieLoginText(stringResource(id = R.string.email))
            MovieTextField(
                value = email.value,
                onValueChange = { email.value = it },
                error = emailError,
                placeholder = stringResource(id = R.string.email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                trailingIcon = null,
                visualTransformation = VisualTransformation.None
            )
            MovieLoginText(stringResource(id = R.string.password))
            MovieTextField(
                value = password.value,
                onValueChange = { password.value = it },
                error = passwordError,
                placeholder = stringResource(id = R.string.password),
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
            MovieLoginText(stringResource(id = R.string.confirm_password))
            MovieTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                error = confirmPasswordError,
                placeholder = stringResource(id = R.string.confirm_password),
                visualTransformation = if (confirmShowPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (confirmShowPassword) {
                        IconButton(onClick = { confirmShowPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { confirmShowPassword = true }) {
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
            MovieButton(
                onClick = {
                    emailError = email.value.isEmpty()
                    passwordError = password.value.isEmpty()
                    confirmPasswordError = confirmPassword.value.isEmpty()
                    if (emailError) {
                        errorMessage = emailEmptyMessage
                    } else if (passwordError) {
                        errorMessage = passwordEmptyMessage
                    } else if (confirmPasswordError) {
                        errorMessage = confirmPasswordEmptyMessage
                    } else if (password.value != confirmPassword.value) {
                        confirmPasswordError = true
                        errorMessage = passwordsNotMatchMessage
                    } else {
                        viewModel.registerUser(email = email.value, password = password.value)
                    }
                }, text = stringResource(id = R.string.sign_up), isLoading = state.isLoading
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string.already_have_account),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp, fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.LoginScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
            LaunchedEffect(key1 = state.isSuccess) {
                scope.launch {
                    if (state.isSuccess!!.isNotEmpty() == true) {
                        val success = state.isSuccess
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpTo(Screens.SignUpScreen.route) {
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
                        confirmPasswordError = true
                        viewModel.resetLoadingState()
                        viewModel.resetErrorState()
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    SignUpScreen(navController = navController)
}