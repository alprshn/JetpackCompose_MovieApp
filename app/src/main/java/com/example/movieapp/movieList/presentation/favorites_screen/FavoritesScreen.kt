    package com.example.movieapp.movieList.presentation.favorites_screen

    import MovieMapper
    import android.net.Uri
    import android.util.Log
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.fillMaxHeight
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.outlined.CalendarToday
    import androidx.compose.material.icons.outlined.ConfirmationNumber
    import androidx.compose.material.icons.outlined.StarBorder
    import androidx.compose.material3.Card
    import androidx.compose.material3.CardDefaults
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.livedata.observeAsState
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.NavHostController
    import coil.compose.rememberImagePainter
    import com.example.movieapp.R
    import com.example.movieapp.movieList.data.local.entity.MovieEntityEn
    import com.example.movieapp.movieList.data.local.entity.MovieEntityTr
    import com.example.movieapp.movieList.presentation.components.MovieCard
    import com.example.movieapp.movieList.presentation.settings_screen.SettingsViewModel
    import com.example.movieapp.movieList.util.Screens
    import com.example.movieapp.ui.theme.backgroundColor
    import com.example.movieapp.ui.theme.darkGreyColor
    import com.example.movieapp.ui.theme.latoFontFamily
    import com.example.movieapp.ui.theme.starColor
    import com.example.movieapp.ui.theme.whiteColor
    import com.google.gson.Gson

    @Composable
    fun FavoritesScreen(
        viewModel: FavoriteViewModel = hiltViewModel(),
        settingsViewModel: SettingsViewModel = hiltViewModel(),
        navController: NavHostController
    ) {
        val favoriteMoviesEn by viewModel.favoriteMoviesEn.observeAsState(emptyList())
        val favoriteMoviesTr by viewModel.favoriteMoviesTr.observeAsState(emptyList())
        val currentLanguage = settingsViewModel.language.observeAsState().value

        LaunchedEffect(currentLanguage) {
            viewModel.getFavoriteMovies(currentLanguage ?: "en")
        }
        val favoriteMovies = if (currentLanguage == "en") favoriteMoviesEn else favoriteMoviesTr

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,

            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.favorites),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = latoFontFamily,
                    modifier = Modifier.padding(top = 16.dp)
                )

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                ) {
                    items(favoriteMovies) { movie ->
                        val movieResult = if (currentLanguage == "en") {
                            MovieMapper().roomMapToResultEn(movie as MovieEntityEn)
                        } else {
                            MovieMapper().roomMapToResultTr(movie as MovieEntityTr)
                        }
                        MovieCard(navController = navController, movie = movieResult, onClick = {
                            val movieJson = Uri.encode(Gson().toJson(movieResult))
                            navController.navigate(Screens.DetailScreen.route + "/$movieJson")
                        })
                    }
                }
            }
        }
    }



