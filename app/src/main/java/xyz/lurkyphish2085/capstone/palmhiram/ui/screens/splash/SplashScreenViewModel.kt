package xyz.lurkyphish2085.capstone.palmhiram.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import xyz.lurkyphish2085.capstone.palmhiram.data.LoanTransactionRepository
import xyz.lurkyphish2085.capstone.palmhiram.data.Resource
import xyz.lurkyphish2085.capstone.palmhiram.data.models.LoanTransaction
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val testRepo: LoanTransactionRepository
): ViewModel() {

    val delayFlow = flow<Boolean> {

        val startingValue = 2
        var currentValue = startingValue
        emit(false)
        while(currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue == 0)
        }
    }

    private var _internetCheckFlow = MutableStateFlow<Resource<List<LoanTransaction>>?>(null)
    val internetCheckFlow: StateFlow<Resource<List<LoanTransaction>>?> = _internetCheckFlow

    fun checkInternet() = viewModelScope.launch {
        _internetCheckFlow.value = Resource.Loading
        val result = testRepo.getLoanTransactionsByUser("0000")
        _internetCheckFlow.value = result
    }
}