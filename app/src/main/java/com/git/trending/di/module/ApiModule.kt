package com.git.trending.di.module

import com.git.trending.data.remote.api.GitApiService
import com.git.trending.util.AppConstant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**Created by Shiv Jalkote on 08-May-2021. **/

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideGSonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gSon: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gSon))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): GitApiService {
        return retrofit
            .build()
            .create(GitApiService::class.java)
    }

}