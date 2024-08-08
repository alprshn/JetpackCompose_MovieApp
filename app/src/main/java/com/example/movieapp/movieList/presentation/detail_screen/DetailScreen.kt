package com.example.movieapp.movieList.presentation.detail_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movieapp.R
import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
import com.example.movieapp.movieList.presentation.components.DetailMovieCardText
import com.example.movieapp.movieList.presentation.settings_screen.SettingsViewModel
import com.example.movieapp.ui.theme.backgroundColor
import com.example.movieapp.ui.theme.bottomBarColor
import com.example.movieapp.ui.theme.latoFontFamily
import com.example.movieapp.ui.theme.releaseDateColor
import com.example.movieapp.ui.theme.starColor
import com.example.movieapp.ui.theme.whiteColor


@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(), movie: Result, navController: NavHostController,settingsViewModel: SettingsViewModel= hiltViewModel()
) {
    val isFavorite by viewModel.isFavorite(movie.id).observeAsState(initial = false)
    val isWatchlist by viewModel.isWatchlist(movie.id).observeAsState(initial = false)

    var currentFavoriteState by remember { mutableStateOf(isFavorite) }
    var currentWatchlistState by remember { mutableStateOf(isWatchlist) }
    val genres by viewModel.genres.observeAsState(initial = emptyList())
    val currentLanguage = settingsViewModel.language.observeAsState().value

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    LaunchedEffect(isWatchlist) {
        currentWatchlistState = isWatchlist
    }
    LaunchedEffect(isFavorite) {
        currentFavoriteState = isFavorite
    }

    LaunchedEffect(currentLanguage) {
        viewModel.getGenre(currentLanguage.toString())
    }
    Log.e("currentLanguage Detail Screen", currentLanguage.toString())
    Scaffold(
        topBar = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.3f)
            ) {
                Image(
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.backdrop_path}"),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            navController.popBackStack()
                        })

                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiaryContainer,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 2.dp)
                        )
                        DetailMovieCardText(
                            text = String.format("%.1f", movie.vote_average),
                            Modifier
                        )
                    }
                }

            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(8.dp)
                    .width(200.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {
                        if (currentFavoriteState) {
                            viewModel.removeFavorite(
                                MovieEntity(
                                    id = movie.id,
                                    userId = "",
                                    adult = movie.adult,
                                    backdrop_path = movie.backdrop_path ?: "Unknown",
                                    genre_ids = movie.genre_ids,
                                    original_language = movie.original_language
                                        ?: "Unknown",
                                    original_title = movie.original_title ?: "Unknown",
                                    overview = movie.overview ?: "Unknown",
                                    popularity = movie.popularity ?: 0.0,
                                    poster_path = movie.poster_path ?: "Unknown",
                                    release_date = movie.release_date ?: "Unknown",
                                    title = movie.title ?: "Unknown",
                                    video = movie.video ?: false,
                                    vote_average = movie.vote_average ?: 0.0,
                                    vote_count = movie.vote_count ?: 0,
                                    isFavorite = false
                                )
                            )
                        } else {
                            viewModel.addFavorite(movie)
                        }
                        currentFavoriteState = !currentFavoriteState
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (currentFavoriteState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (currentFavoriteState) Color.Red else MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                )
                IconButton(
                    onClick = {
                        if (currentWatchlistState) {
                            viewModel.removeWatchlist(
                                FirebaseMovieEntity(
                                    id = movie.id,
                                    userId = "",
                                    adult = movie.adult,
                                    backdrop_path = movie.backdrop_path,
                                    genre_ids = movie.genre_ids,
                                    original_language = movie.original_language,
                                    original_title = movie.original_title,
                                    overview = movie.overview,
                                    popularity = movie.popularity,
                                    poster_path = movie.poster_path,
                                    release_date = movie.release_date,
                                    title = movie.title,
                                    video = movie.video,
                                    vote_average = movie.vote_average,
                                    vote_count = movie.vote_count,
                                    addedToWatchlist = false
                                )
                            )
                        } else {
                            viewModel.addWatchlist(movie)
                        }
                        currentWatchlistState = !currentWatchlistState
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (currentWatchlistState) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Watchlist",
                            tint = if (currentWatchlistState) Color.Blue else MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

        },
        modifier = Modifier
            .fillMaxSize(),
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp, top = it.calculateTopPadding())
                    .background(MaterialTheme.colorScheme.background)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(25.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = movie.original_title,
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontFamily = latoFontFamily
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Column(
                            ) {
                                DetailMovieCardText(
                                    text = stringResource(id = R.string.release_date),
                                    modifier = Modifier.padding(end = 30.dp)
                                )
                                Text(
                                    text = movie.release_date,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.inversePrimary,
                                    fontFamily = latoFontFamily
                                )
                            }
                            Column() {
                                DetailMovieCardText(
                                    text = stringResource(id = R.string.genre),
                                    modifier = Modifier
                                )
                                Text(
                                    text = movie.genre_ids
                                        .mapNotNull { id -> genres.find { it.id == id }?.name }
                                        .joinToString(", "),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontFamily = latoFontFamily
                                )
                            }
                        }
                        DetailMovieCardText(
                            text = stringResource(id = R.string.about_movie),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        //LazyColumn(modifier = Modifier.padding(bottom = 50.dp)) {
                        Text(
                            text = movie.overview,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontFamily = latoFontFamily
                        )

                    }

                }
            }

        }
    )


}

