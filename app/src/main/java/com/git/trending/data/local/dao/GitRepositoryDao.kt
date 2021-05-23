package com.git.trending.data.local.dao

import androidx.room.*
import com.git.trending.data.local.entity.GitRepositoryEntity


/**Created by Shiv Jalkote on 08-May-2021. **/

@Dao
interface GitRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: GitRepositoryEntity): Long

    @Query("SELECT * FROM GitRepositoryEntity")
    suspend fun getRepositories(): List<GitRepositoryEntity>

    @Update
    suspend fun update(gitRepo: GitRepositoryEntity)

    @Query("SELECT * FROM GitRepositoryEntity WHERE id=:id")
    suspend fun getGitRepositoryById(id: Int): GitRepositoryEntity?

    @Query("SELECT * FROM GitRepositoryEntity WHERE name like '%' || :name || '%' ")
    suspend fun getRepositoryByName(name: String): List<GitRepositoryEntity>


}