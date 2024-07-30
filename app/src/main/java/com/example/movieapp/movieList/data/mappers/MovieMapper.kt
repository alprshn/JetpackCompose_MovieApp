import com.example.movieapp.movieList.data.remote.entity.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.entity.MovieEntity
import com.example.movieapp.movieList.data.remote.api.response.search_data.Result

class MovieMapper {

    fun mapToMovieEntity(result: Result, userId: String): MovieEntity {
        return MovieEntity(
            id = result.id,
            userId = userId,
            adult = result.adult,
            backdrop_path = result.backdrop_path ?: "Unknown",
            genre_ids = result.genre_ids,
            original_language = result.original_language ?: "Unknown",
            original_title = result.original_title ?: "Unknown",
            overview = result.overview ?: "Unknown",
            popularity = result.popularity,
            poster_path = result.poster_path ?: "Unknown",
            release_date = result.release_date ?: "Unknown",
            title = result.title ?: "Unknown",
            video = result.video,
            vote_average = result.vote_average,
            vote_count = result.vote_count,
            isFavorite = false
        )
    }

    fun mapToFirebaseMovieEntity(result: Result, userId: String): FirebaseMovieEntity {
        return FirebaseMovieEntity(
            id = result.id,
            userId = userId,
            adult = result.adult,
            backdrop_path = result.backdrop_path ?: "Unknown",
            genre_ids = result.genre_ids,
            original_language = result.original_language ?: "Unknown",
            original_title = result.original_title ?: "Unknown",
            overview = result.overview ?: "Unknown",
            popularity = result.popularity,
            poster_path = result.poster_path ?: "Unknown",
            release_date = result.release_date ?: "Unknown",
            title = result.title ?: "Unknown",
            video = result.video,
            vote_average = result.vote_average,
            vote_count = result.vote_count,
            addedToWatchlist = false
        )
    }

    fun firestoreMapToResult(firebaseMovieEntity: FirebaseMovieEntity): Result {
        return Result(
            adult = firebaseMovieEntity.adult,
            backdrop_path = firebaseMovieEntity.backdrop_path!!,
            genre_ids = firebaseMovieEntity.genre_ids,
            id = firebaseMovieEntity.id!!,
            original_language = firebaseMovieEntity.original_language ?: "Unknown",
            original_title = firebaseMovieEntity.original_title ?: "Unknown",
            overview = firebaseMovieEntity.overview ?: "Unknown",
            popularity = firebaseMovieEntity.popularity,
            poster_path = firebaseMovieEntity.poster_path ?: "Unknown",
            release_date = firebaseMovieEntity.release_date ?: "Unknown",
            title = firebaseMovieEntity.title ?: "Unknown",
            video = firebaseMovieEntity.video,
            vote_average = firebaseMovieEntity.vote_average ?: 0.0,
            vote_count = firebaseMovieEntity.vote_count ?: 0
        )
    }


    fun roomMapToResult(movieEntity: MovieEntity): Result {
        return Result(
            adult = movieEntity.adult,
            backdrop_path = movieEntity.backdrop_path ?: "Unknown",
            genre_ids = movieEntity.genre_ids,
            id = movieEntity.id!!,
            original_language = movieEntity.original_language ?: "Unknown",
            original_title = movieEntity.original_title ?: "Unknown",
            overview = movieEntity.overview ?: "Unknown",
            popularity = movieEntity.popularity,
            poster_path = movieEntity.poster_path ?: "Unknown",
            release_date = movieEntity.release_date ?: "Unknown",
            title = movieEntity.title ?: "Unknown",
            video = movieEntity.video,
            vote_average = movieEntity.vote_average ?: 0.0,
            vote_count = movieEntity.vote_count ?: 0
        )
    }
}