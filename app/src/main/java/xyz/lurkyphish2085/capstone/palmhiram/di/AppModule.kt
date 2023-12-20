package xyz.lurkyphish2085.capstone.palmhiram.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.lurkyphish2085.capstone.palmhiram.data.AuthRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.AuthRepositoryImpl
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepositoryImpl
import xyz.lurkyphish2085.capstone.palmhiram.data.UserProfilesRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.UserProfilesRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideLoanTransactionRepository(impl: LoanTransactionRepositoryImpl): LoanTransactionRepository = impl

    @Provides
    fun provideUserProfilesRepository(impl: UserProfilesRepositoryImpl): UserProfilesRepository = impl
}