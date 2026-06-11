package com.example.passwordmanager.data.infra

import com.example.passwordmanager.domain.usecase.PasswordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindPasswordRepository(
        impl: PasswordRepositoryImpl
    ): PasswordRepository
}
