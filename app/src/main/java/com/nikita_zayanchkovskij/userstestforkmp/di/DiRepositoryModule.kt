package com.nikita_zayanchkovskij.userstestforkmp.di

import com.nikita_zayanchkovskij.userstestforkmp.data.repositories.UsersRepositoryImplementation
import com.nikita_zayanchkovskij.userstestforkmp.domain.repositories.IUsersRepositoryDefinition
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DiRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUsersRepository(
        usersRepoImpl: UsersRepositoryImplementation
    ): IUsersRepositoryDefinition
}