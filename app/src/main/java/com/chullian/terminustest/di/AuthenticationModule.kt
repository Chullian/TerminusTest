package com.chullian.terminustest.di

import com.chullian.terminustest.data.repository.AuthRepository
import com.chullian.terminustest.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by binMammootty on 02/01/2022.
 */
@Module
@InstallIn(SingletonComponent::class)

abstract class AuthenticationModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

}