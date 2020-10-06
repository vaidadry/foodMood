package vaida.dryzaite.foodmood.ui.homePage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Transformations
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import vaida.dryzaite.foodmood.repository.RecipeDatabaseRepository
import vaida.dryzaite.foodmood.utilities.recipe1

//
//@RunWith(JUnit4::class)
//class HomeViewModelTest {
//
//    @get:Rule
//    var rule: TestRule = InstantTaskExecutorRule()
//
//
//    @Mock
//    lateinit var repository: RecipeDatabaseRepository
//    private lateinit var viewModel: HomeViewModel
//
//    @Before
//    fun init() {
//        MockitoAnnotations.initMocks(this)
//        viewModel = HomeViewModel(repository)
//    }
//
////    @Test
////    fun loadInitialRecipeList_meal0_ReturnsAll() {
////
////        //Given
////
////    }
//
//}
