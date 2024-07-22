import com.example.movieapp.movieList.data.local.movie.FirebaseMovieEntity
import com.example.movieapp.movieList.data.local.movie.MovieEntity
import com.example.movieapp.movieList.data.remote.respond.Result

class MovieMapper {

    fun mapToMovieEntity(result: Result, userId: String): MovieEntity {
        return MovieEntity(
            id = result.id,
            userId = userId,
            adult = result.adult,
            backdrop_path = result.backdrop_path,
            genre_ids = result.genre_ids,
            original_language = result.original_language,
            original_title = result.original_title,
            overview = result.overview,
            popularity = result.popularity,
            poster_path = result.poster_path,
            release_date = result.release_date,
            title = result.title,
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
            backdrop_path = result.backdrop_path,
            genre_ids = result.genre_ids,
            original_language = result.original_language,
            original_title = result.original_title,
            overview = result.overview,
            popularity = result.popularity,
            poster_path = result.poster_path,
            release_date = result.release_date,
            title = result.title,
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
            original_language = firebaseMovieEntity.original_language!!,
            original_title = firebaseMovieEntity.original_title!!,
            overview = firebaseMovieEntity.overview!!,
            popularity = firebaseMovieEntity.popularity,
            poster_path = firebaseMovieEntity.poster_path!!,
            release_date = firebaseMovieEntity.release_date!!,
            title = firebaseMovieEntity.title!!,
            video = firebaseMovieEntity.video,
            vote_average = firebaseMovieEntity.vote_average!!,
            vote_count = firebaseMovieEntity.vote_count!!
        )
    }


    fun roomMapToResult(movieEntity: MovieEntity): Result {
        return Result(
            adult = movieEntity.adult,
            backdrop_path = movieEntity.backdrop_path!!,
            genre_ids = movieEntity.genre_ids,
            id = movieEntity.id!!,
            original_language = movieEntity.original_language!!,
            original_title = movieEntity.original_title!!,
            overview = movieEntity.overview!!,
            popularity = movieEntity.popularity,
            poster_path = movieEntity.poster_path!!,
            release_date = movieEntity.release_date!!,
            title = movieEntity.title!!,
            video = movieEntity.video,
            vote_average = movieEntity.vote_average!!,
            vote_count = movieEntity.vote_count!!
        )
    }

    // Yardımcı fonksiyonlar
    private fun toJsonFromList(list: List<Int>): String {
        // Burada JSON'a dönüştürme işlemini yapın
        return list.joinToString(",") { it.toString() }
    }

    private fun fromJsonToList(json: String): List<Int> {
        // Burada JSON'dan listeye dönüştürme işlemini yapın
        return json.split(",").map { it.toInt() }
    }
}