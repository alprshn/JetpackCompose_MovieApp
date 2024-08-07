package com.example.movieapp.movieList.data.repository


import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {
    override suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(value = Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(value = Resource.Success(data = result))
        }.catch {
            emit(value = Resource.Error(it.message.toString()))
        }
    }

    override suspend fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(value = Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.sendEmailVerification()?.await()
            emit(value = Resource.Success(data = result))
        }.catch {
            emit(value = Resource.Error(it.message.toString()))
        }
    }


    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun signOut(): Flow<Resource<Unit>> {
        return flow {
            emit(value = Resource.Loading())
            try {
                firebaseAuth.signOut()
                emit(value = Resource.Success(Unit))
            } catch (e: Exception) {
                emit(value = Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }.catch {
            emit(value = Resource.Error(it.message.toString()))
        }
    }

    override fun isEmailVerified(): Flow<Resource<Boolean>> = flow {
        emit(value = Resource.Loading())
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            emit(Resource.Success(currentUser.isEmailVerified))
        } else {
            emit(Resource.Error("Kullanıcı oturumu açık değil"))
        }
    }

}
