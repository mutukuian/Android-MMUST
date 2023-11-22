package com.example.androidbootcamp.common


import com.example.androidbootcamp.data.repository.AuthRepositoryImpl
import com.example.androidbootcamp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Di {

    @Provides
    @Singleton
    fun providesFirebaseAuth()= FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthenticationRepository(firebaseAuth: FirebaseAuth):AuthRepository{
        return AuthRepositoryImpl(firebaseAuth)
    }
}