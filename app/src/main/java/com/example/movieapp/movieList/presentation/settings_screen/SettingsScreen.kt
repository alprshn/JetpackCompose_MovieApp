package com.example.movieapp.movieList.presentation.settings_screen

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.R
import com.example.movieapp.movieList.data.repository.DataStorePreferenceRepository
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.colorBlack
import com.example.movieapp.ui.theme.darkGreyColor
import com.example.movieapp.ui.theme.latoFontFamily
import com.example.movieapp.ui.theme.whiteColor
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val isChecked by viewModel.isDarkModeEnabled.collectAsState()
    var mExpanded by remember { mutableStateOf(false) }

    Log.e("isChecked", isChecked.toString())
    val languages = listOf("English" to "en", "Türkçe" to "tr")
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val context = LocalContext.current

    val currentLanguage = viewModel.language.observeAsState().value
    val selectedLanguageLabel = languages.find { it.second == currentLanguage }?.first ?: "Languages"
    var showDialog by remember { mutableStateOf(false) }  // AlertDialog için state

    // val selectedLanguageLabel =
    //languages.find { it.second == selectedLanguage }?.first ?: "Languages"
    val state by viewModel.signOutState.collectAsState()



    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
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
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterStart)

                        .clickable {
                            navController.popBackStack()
                        })
                Text(
                    text = stringResource(id = R.string.settings),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        },
        content = {
            Surface(
                color = MaterialTheme.colorScheme.background,
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
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface, contentColor =MaterialTheme.colorScheme.surface ),
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
                                .background(MaterialTheme.colorScheme.surface)
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.dark_mode),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Switch(
                                checked = isChecked,
                                onCheckedChange = { isCheckedNew ->
                                    if (isChecked != isCheckedNew) { // Yalnızca değer değişirse işlem yap
                                        viewModel.toggleDarkMode()
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = whiteColor,
                                    uncheckedThumbColor = darkGreyColor
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 4.dp)

                            )
                        }

                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                                .background(MaterialTheme.colorScheme.surface)
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.languages),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            Text(
                                text = selectedLanguageLabel,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
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
                                        text = { Text(label, color = MaterialTheme.colorScheme.onPrimary) },
                                        onClick = {
                                            viewModel.viewModelScope.launch {
                                                viewModel.saveLanguage(code)
                                            }

                                            Log.e("selectedLanguage code", code.toString())

                                            mExpanded = false
                                            // Yeni dili uygulamak için aktiviteyi yeniden başlat
                                            navController.navigate(Screens.SettingsScreen.route) {
                                                popUpTo(Screens.SettingsScreen.route) {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 6.dp)
                            .height(70.dp)
                            .clickable {
                                showDialog = true
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .fillMaxHeight(), // Dikeyde tam yüksekliği kapla ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(
                                    text = stringResource(id = R.string.logout),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }

                    }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = true },
                            containerColor = MaterialTheme.colorScheme.surface,
                            title = { Text(text = stringResource(id = R.string.logout)) },
                            text = { Text(stringResource(id = R.string.logout_confirmation)) },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDialog = false  // Dialog'u kapat
                                        viewModel.signOut()
                                    }
                                ) {
                                    Text(stringResource(id = R.string.yes),color = MaterialTheme.colorScheme.surfaceTint)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text(stringResource(id = R.string.no),color = MaterialTheme.colorScheme.surfaceTint)
                                }
                            }
                        )
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
            viewModel.resetSignOutState()
        }
        if (state.isError.isNotEmpty()) {
            // Error handling burada yapılabilir, örneğin bir hata mesajı gösterebilirsiniz.
            Log.e("SettingsScreen", "Sign out failed: ${state.isError}")
            viewModel.resetSignOutState()
        }
    }
}


@Preview
@Composable
fun settingsScreenPreview() {
    val navController = rememberNavController()

    SettingsScreen(navController)
}


@Composable
fun SetLanguage(languageCode: String) {
    val locale = Locale(languageCode)
    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)

}


