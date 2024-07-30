package com.example.movieapp.movieList.presentation.settings_screen

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.R
import com.example.movieapp.movieList.presentation.viewmodel.AuthenticationViewModel
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.latoFontFamily
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),
    authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {
    val isChecked by viewModel.isDarkModeEnabled.collectAsState()
    var mExpanded by remember { mutableStateOf(false) }

    val languages = listOf("English" to "en", "Türkçe" to "tr")
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val context = LocalContext.current

    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val selectedLanguageLabel = languages.find { it.second == selectedLanguage }?.first ?: "Languages"
    val state by authenticationViewModel.signOutState.collectAsState()

    Log.e("selectedLanguage", selectedLanguage.toString())


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        containerColor = backgroundColor,
        contentColor = backgroundColor,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = Color(0xFFB0B0B0),
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterStart)

                        .clickable {
                            navController.popBackStack()
                        })
                Text(
                    text = stringResource(id = R.string.settings),
                    fontSize = 24.sp,
                    color = Color.White,
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        },
        content = {
            Surface(
                color = backgroundColor,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Card(
                        colors = CardDefaults.cardColors(containerColor = bottomBarColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 6.dp)
                            .height(70.dp)
                            .clickable {
                                viewModel.toggleDarkMode()
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(bottomBarColor)
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.dark_mode),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Switch(
                                checked = isChecked,
                                onCheckedChange = {
                                    viewModel.toggleDarkMode()
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    uncheckedThumbColor = Color.Gray
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 4.dp)

                            )
                        }

                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = bottomBarColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 6.dp)
                            .height(70.dp)
                            .clickable { mExpanded = !mExpanded } // Kart tıklanabilir yapıldı
                            .onGloballyPositioned { coordinates ->
                                mTextFieldSize = coordinates.size.toSize()
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(bottomBarColor)
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.languages),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            Text(
                                text = selectedLanguageLabel,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            DropdownMenu(
                                expanded = mExpanded,
                                onDismissRequest = { mExpanded = false },
                                modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() }) // Menü genişliğini kart genişliğiyle sınırla
                            ) {
                                languages.forEach { (label, code) ->
                                    DropdownMenuItem(
                                        text = { Text(label, color = Color.Black) },
                                        onClick = {
                                            viewModel.setLanguage(code)
                                            Log.e("selectedLanguage code", code.toString())
                                            setLocale(context, code)
                                            mExpanded = false
                                            // Yeni dili uygulamak için aktiviteyi yeniden başlat
                                            navController.navigate(Screens.SettingsScreen.route) {
                                                popUpTo(Screens.SettingsScreen.route) { inclusive = true }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = bottomBarColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 6.dp)
                            .height(70.dp)
                            .clickable {
                                authenticationViewModel.signOut()

                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(bottomBarColor)
                                .fillMaxHeight(), // Dikeyde tam yüksekliği kapla ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }else{
                                Text(
                                    text = stringResource(id = R.string.logout),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }

                    }
                }
            }

        },
        )

    LaunchedEffect(key1 = state.isSuccess, key2 = state.isError) {
        if (state.isSuccess!!.isNotEmpty()) {
            navController.navigate(Screens.LoginScreen.route) {
                popUpTo(Screens.SettingsScreen.route) {
                    inclusive = true
                }
            }
            authenticationViewModel.resetSignOutState()
        }
        if (state.isError.isNotEmpty()) {
            // Error handling burada yapılabilir, örneğin bir hata mesajı gösterebilirsiniz.
            Log.e("SettingsScreen", "Sign out failed: ${state.isError}")
            authenticationViewModel.resetSignOutState()
        }
    }
}


@Preview
@Composable
fun settingsScreenPreview() {
    val navController = rememberNavController()

    SettingsScreen(navController)
}


fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = context.resources
    val config = Configuration(resources.configuration)

    config.setLocale(locale)

    resources.updateConfiguration(config, resources.displayMetrics)
}