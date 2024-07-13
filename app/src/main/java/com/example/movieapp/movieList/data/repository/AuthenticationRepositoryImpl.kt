package com.example.movieapp.movieList.data.repository

import com.example.movieapp.movieList.domain.repository.AuthenticationRepository
import com.example.movieapp.movieList.util.Constants
import com.example.movieapp.movieList.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthenticationRepository {
    var operationSuccessful = false
    override fun isUserAuthenticatedInFirebase(): Boolean {
        return auth.currentUser != null
    }

    @ExperimentalCoroutinesApi
    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSignIn(email: String, password: String): Flow<Resource<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Resource.Loading())
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessful = true
            }.await()
            emit(Resource.Success(operationSuccessful))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun firebaseSignOut(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            auth.signOut()
            emit(Resource.Success(true))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun firebaseSignUp(email: String, password: String): Flow<Resource<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Resource.Loading())
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessful = true
            }
            if (operationSuccessful) {
                val userId = auth.currentUser?.uid!!
                val obj = User(userId= userId, email = email,password = password )
                firestore.collection(Constants.COLLECTION_NAME_USERS).document(userId).set(obj).addOnSuccessListener {

                }.await()
                emit(Resource.Success(operationSuccessful))
            }
        }catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}