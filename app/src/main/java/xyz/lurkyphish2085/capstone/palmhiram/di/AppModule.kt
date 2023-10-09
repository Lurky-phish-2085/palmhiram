package xyz.lurkyphish2085.capstone.palmhiram.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.lurkyphish2085.capstone.palmhiram.data.AuthRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl) = impl
}