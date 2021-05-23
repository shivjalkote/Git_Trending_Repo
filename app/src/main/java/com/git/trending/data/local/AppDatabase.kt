package com.git.trending.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.git.trending.data.local.dao.GitRepositoryDao
import com.git.trending.data.local.entity.GitRepositoryEntity

/**Created by Shiv Jalkote on 08-May-2021. **/

@Database(entities = [GitRepositoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGitRepoDao(): GitRepositoryDao

}