package com.git.trending.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.git.trending.data.local.GitRepositoryDaoStub
import com.git.trending.data.local.mapper.LocalCacheMapper
import com.git.trending.data.remote.GitApiServiceStub
import com.git.trending.data.remote.api.GitApiService
import com.git.trending.data.remote.mapper.ApiResponseMapper
import com.git.trending.data.repository.GitRepoRepository
import com.git.trending.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


/**Created by Shiv Jalkote on 13-May-2021. **/


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GitRepositoryViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var mViewModel: GitRepositoryViewModel

    private val cacheMapper = LocalCacheMapper()
    private val responseMapper = ApiResponseMapper()
    private val fakeRepoDao = GitRepositoryDaoStub()
    private val fakeApiService = GitApiServiceStub()

    @Mock
    lateinit var apiService: GitApiService


    @Before
    fun setup() {
        val repository = GitRepoRepository(fakeRepoDao, apiService, cacheMapper, responseMapper)
        mViewModel = GitRepositoryViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun fetchRepositoryValidateCount_returnTrue() {
        runBlockingTest {
            Mockito.`when`(apiService.getGitRepository())
                .thenReturn(fakeApiService.getGitRepository())

            val repository = GitRepoRepository(fakeRepoDao, apiService, cacheMapper, responseMapper)
            mViewModel = GitRepositoryViewModel(repository)

            mViewModel.fetchRepositories()
            val count = mViewModel.getRepositoryCount()
            Assert.assertEquals(5, count)
        }
    }


    @Test
    fun fetchLocalRepositoryWithoutLoading_returnFalse() {
        mViewModel.fetchLocalRepository(1)
        val localRepoResponse = mViewModel.dataStateRepository.value
        val localRepo =
            if (localRepoResponse is DataState.Success) localRepoResponse.data else null
        Assert.assertEquals(null, localRepo)
    }

    @Test
    fun fetchLocalRepositoryWithLoadingList_returnTrue() {
        runBlockingTest {
            Mockito.`when`(apiService.getGitRepository())
                .thenReturn(fakeApiService.getGitRepository())

            mViewModel.fetchRepositories()
            mViewModel.fetchLocalRepository(1)
            val localRepoResponse = mViewModel.dataStateRepository.value
            val localRepo =
                if (localRepoResponse is DataState.Success) localRepoResponse.data else null
            Assert.assertEquals(1, localRepo?.id)
        }
    }

    @Test
    fun fetchRemoteRepositoryIfFound_returnTrue() {
        runBlockingTest {
            val ownerName = "mojito"
            val repoName = "Android Unit Testing"
            Mockito.`when`(apiService.getGitRepository(ownerName, repoName))
                .thenReturn(fakeApiService.getGitRepository(ownerName, repoName))

            mViewModel.fetchRepository(ownerName, repoName)
            val repoResult = mViewModel.dataStateRepository.value
            val repo = if (repoResult is DataState.Success) repoResult.data else null
            Assert.assertEquals(repoName, repo?.name)
        }
    }

    @Test
    fun searchLocalRepoByNameIfFound_returnTrue() {
        runBlockingTest {
            Mockito.`when`(apiService.getGitRepository())
                .thenReturn(fakeApiService.getGitRepository())

            mViewModel.fetchRepositories()
            mViewModel.fetchLocalRepositories("Repo 1")
            val repoResult = mViewModel.dataState.value
            val repoList = if (repoResult is DataState.Success) repoResult.data else null
            Assert.assertEquals("Repo 1", repoList?.get(0)?.name)
        }
    }


}