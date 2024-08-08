    @file:OptIn(ExperimentalMaterial3Api::class)

    package com.example.movieapp.movieList.presentation.search_screen


    import MovieMapper
    import android.net.Uri
    import android.util.Log
    import androidx.compose.foundation.ExperimentalFoundationApi
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
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
    import androidx.compose.foundation.lazy.LazyRow
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.lazy.rememberLazyListState
    import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
    import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
    import androidx.compose.foundation.pager.HorizontalPager
    import androidx.compose.foundation.pager.PagerState
    import androidx.compose.foundation.pager.rememberPagerState
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Search
    import androidx.compose.material.icons.filled.Settings
    import androidx.compose.material.icons.outlined.CalendarToday
    import androidx.compose.material.icons.outlined.ConfirmationNumber
    import androidx.compose.material.icons.outlined.StarBorder
    import androidx.compose.material3.Card
    import androidx.compose.material3.CardDefaults
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.IconButtonDefaults
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.Surface

    import androidx.compose.material3.Text
    import androidx.compose.material3.TextFieldDefaults

    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.MutableState
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.derivedStateOf
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.livedata.observeAsState
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment

    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.graphicsLayer
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.platform.LocalConfiguration
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.lerp
    import androidx.compose.ui.unit.sp
    import androidx.compose.ui.util.lerp
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.NavHostController
    import androidx.paging.LoadState
    import androidx.paging.compose.collectAsLazyPagingItems
    import coil.compose.rememberImagePainter
    import com.example.movieapp.R
    import com.example.movieapp.movieList.data.remote.api.response.search_data.Result
    import com.example.movieapp.movieList.presentation.components.MovieRowList
    import com.example.movieapp.movieList.presentation.components.MovieSectionTitle
    import com.example.movieapp.movieList.presentation.settings_screen.SettingsViewModel
    import com.example.movieapp.movieList.util.Screens
    import com.example.movieapp.ui.theme.backgroundColor
    import com.example.movieapp.ui.theme.bottomBarColor
    import com.example.movieapp.ui.theme.latoFontFamily
    import com.example.movieapp.ui.theme.searchTextColor
    import com.example.movieapp.ui.theme.starColor
    import com.example.movieapp.ui.theme.whiteColor
    import com.google.gson.Gson
    import kotlinx.coroutines.delay
    import kotlin.math.absoluteValue


    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun SearchScreen(
        viewModel: SearchViewModel = hiltViewModel(),
        settingsViewModel: SettingsViewModel = hiltViewModel(),
        navController: NavHostController,
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val query: MutableState<String> = remember { mutableStateOf("") }
        val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
        val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
        val currentLanguage = settingsViewModel.language.observeAsState().value

        LaunchedEffect(currentLanguage) {
            Log.e("SearchScreen language", currentLanguage.toString())
            viewModel.popularMovies(settingsViewModel.getApiLanguage())
            Log.d("SearchScreen", "Loading popular movies...")
        }

        val favoriteMovies by viewModel.favoriteMovies.observeAsState(emptyList())
        val watchlistMovies by viewModel.watchlistMovies.observeAsState(emptyList())

        LaunchedEffect(Unit) {
            viewModel.getWatchlistMovies()
        }
        LaunchedEffect(Unit) {
            viewModel.getFavoriteMovies()
        }


        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                ) {
                    OutlinedTextField(
                        value = query.value,
                        onValueChange = {
                            query.value = it
                            Log.e("SearchScreen OutlinedTextField", currentLanguage.toString())
                            viewModel.search(query.value, settingsViewModel.getApiLanguage())
                        },
                        enabled = true,
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                tint = MaterialTheme.colorScheme.onPrimary,
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.search_movie),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.onPrimary,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            disabledTextColor = MaterialTheme.colorScheme.onBackground,
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .height(56.dp)
                            .padding(end = 8.dp).weight(1f)
                    )

                    IconButton(
                        onClick = {
                            navController.navigate(Screens.SettingsScreen.route)
                        },
                        modifier = Modifier.size(56.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Icon Button",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }

                if (query.value.isEmpty()) {
                    LazyColumn {
                        item {
                            MovieRowList(
                                title = stringResource(id = R.string.popular_movies),
                                movies = popularMovies.itemSnapshotList.items,
                                navController = navController
                            )
                            MovieRowList(
                                title = stringResource(id = R.string.favorites),
                                movies = favoriteMovies.map { MovieMapper().roomMapToResult(it) },
                                navController = navController
                            )
                            MovieRowList(
                                title = stringResource(id = R.string.watchlist),
                                movies = watchlistMovies.map { MovieMapper().firestoreMapToResult(it) },
                                navController = navController
                            )
                            MovieRowList(
                                title = stringResource(id = R.string.comedy),
                                movies = popularMovies.itemSnapshotList.items.filter { it.genre_ids.contains(35) },
                                navController = navController
                            )
                        }
                    }
                    popularMovies.apply {
                        when {
                            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(58.dp)
                                )
                            }

                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                Text(text = stringResource(id = R.string.error_loading_movies))
                            }

                            loadState.refresh is LoadState.NotLoading -> {
                            }
                        }
                    }
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,

                        ) {
                        searchResults.apply {
                            when {
                                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                    item {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.size(58.dp)
                                            )
                                        }
                                    }
                                }

                                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                    item {
                                        Text(text = stringResource(id = R.string.error))
                                    }
                                }

                                loadState.refresh is LoadState.NotLoading -> {
                                }
                            }

                        }
                        items(searchResults.itemCount) { index ->
                            searchResults[index]?.let {
                                SearchMovieContentItem(it, navController)
                            }
                        }


                    }
                }


            }
        }
    }


    @Composable
    fun SearchMovieContentItem(movie: Result, navController: NavHostController) {

        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .clickable {
                    val movieJson = Uri.encode(Gson().toJson(movie))
                    Log.e("MovieJson", movieJson)
                    navController.navigate(Screens.DetailScreen.route + "/$movieJson")
                },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .height(275.dp)
                        .background(MaterialTheme.colorScheme.onPrimary)
                ) {
                    Image(
                        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/original${movie.poster_path}"),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                val releaseYear =
                    if (movie.release_date.isNullOrEmpty() || movie.release_date.length < 4) {
                        "Unknown"
                    } else {
                        movie.release_date.substring(0, 4)
                    }
                Text(
                    text = "${movie.title} (${releaseYear})",
                    modifier = Modifier
                        .padding(4.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

        }
    }
