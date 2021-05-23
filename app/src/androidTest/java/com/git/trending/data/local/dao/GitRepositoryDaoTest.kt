package com.git.trending.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.git.trending.data.local.AppDatabase
import com.git.trending.data.local.entity.GitRepositoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith


/**Created by Shiv Jalkote on 11-May-2021. **/

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class GitRepositoryDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: GitRepositoryDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getGitRepoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun getGitRepoEntityInstance(): GitRepositoryEntity {
        return GitRepositoryEntity(
            1, "Git-Trending-Repo",
            "This demo assignment",
            1, "Shiv Jalkote", "http://app.github.com",
            12, 45
        )
    }

    @Test
    fun insertRepositoryTest() {
        runBlockingTest {
            val realInstance = getGitRepoEntityInstance()
            dao.insert(realInstance)
            val cachedInstance = dao.getGitRepositoryById(1)
            Assert.assertEquals(realInstance.id, cachedInstance?.id)
        }
    }

    @Test
    fun updateRepositoryTest() {
        runBlockingTest {
            val realInstance = getGitRepoEntityInstance()
            dao.insert(realInstance)
            realInstance.ownerName = "New owner Shiv"
            dao.update(realInstance)
            val cachedInstance = dao.getGitRepositoryById(1)
            Assert.assertEquals(realInstance.ownerName, cachedInstance?.ownerName)
        }
    }


    @Test
    fun checkDuplicateRecordInsertion() {
        runBlockingTest {
            val realInstance = getGitRepoEntityInstance()
            dao.insert(realInstance)
            dao.insert(realInstance)
            val repoList = dao.getRepositories()
            Assert.assertEquals(1, repoList.size)
        }
    }


    @Test
    fun checkRepoSearchByName(){
        runBlockingTest {
            val realInstance = getGitRepoEntityInstance()
            dao.insert(realInstance)

            val repoList = dao.getRepositoryByName("Javascript")
            Assert.assertEquals(0, repoList.size)
        }
    }


}