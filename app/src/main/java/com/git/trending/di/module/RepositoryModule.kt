package com.git.trending.di.module

import com.git.trending.data.local.dao.GitRepositoryDao
import com.git.trending.data.local.mapper.LocalCacheMapper
import com.git.trending.data.remote.api.GitApiService
import com.git.trending.data.remote.mapper.ApiResponseMapper
import com.git.trending.data.repository.GitRepoRepository
import com.git.trending.data.repository.IGitRepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**Created by Shiv Jalkote on 09-May-2021. **/

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepoRepository(
        gitRepositoryDao: GitRepositoryDao,
        apiService: GitApiService,
        cacheMapper: LocalCacheMapper,
        responseMapper: ApiResponseMapper
    ): GitRepoRepository {
        return GitRepoRepository(gitRepositoryDao, apiService, cacheMapper, responseMapper)
    }

    @Singleton
    @Provides
    fun provideIGitRepoRepository(gitRepoRepository: GitRepoRepository): IGitRepoRepository {
        return gitRepoRepository
    }

    /*@Singleton
    @Provides
    fun provideDefaultCoroutineScope(): CoroutineScope {

    }*/


}