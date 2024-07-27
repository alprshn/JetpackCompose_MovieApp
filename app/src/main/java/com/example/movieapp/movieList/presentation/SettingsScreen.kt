package com.example.movieapp.movieList.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.movieList.util.Screens
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.latoFontFamily
import com.google.gson.Gson

@Composable
fun SettingsScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        containerColor = backgroundColor,
        contentColor = backgroundColor,
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp,start = 16.dp,end = 16.dp),
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
                    text = "Settings",
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(listOf("Dark Mode", "Turkish", "Logout")) { item ->
                        val isChecked = remember { mutableStateOf(false) }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = bottomBarColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 7.dp, horizontal = 6.dp)
                                .height(55.dp)
                                .clickable {
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(bottomBarColor),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(vertical = 2.dp)
                                        .align(Alignment.CenterVertically)


                                )
                                if (item == "Dark Mode") {
                                    Switch(
                                        checked = isChecked.value,
                                        onCheckedChange = {
                                            isChecked.value = it
                                        },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            uncheckedThumbColor = Color.Gray
                                        ),
                                        modifier = Modifier.align(Alignment.CenterVertically)

                                    )
                                }
                            }

                        }
                    }
                }
            }
        },

        )
}


@Preview
@Composable
fun settingsScreenPreview() {
    val navController = rememberNavController()

    SettingsScreen(navController)
}