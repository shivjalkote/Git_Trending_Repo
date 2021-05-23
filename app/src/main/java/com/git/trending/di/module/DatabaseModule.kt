package com.git.trending.di.module

import android.content.Context
import androidx.room.Room
import com.git.trending.data.local.dao.GitRepositoryDao
import com.git.trending.data.local.AppDatabase
import com.git.trending.util.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**Created by Shiv Jalkote on 08-May-021. **/

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppConstant.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideGitRepoDao(database: AppDatabase): GitRepositoryDao {
        return database.getGitRepoDao()
    }

}