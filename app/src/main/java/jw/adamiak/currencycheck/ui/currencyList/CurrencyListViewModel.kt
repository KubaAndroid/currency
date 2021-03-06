package jw.adamiak.currencycheck.ui.currencyList

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
	private val repository: CurrencyRepository
): ViewModel() {

	private var _response: Flow<PagingData<Currency>>? = null
	val response: LiveData<PagingData<Currency>>?
		get() = _response?.asLiveData()

//	private var _isLoading = MutableLiveData<Boolean>()
//	val isLoading: LiveData<Boolean>
//		get() = _isLoading

	private fun getCurrenciesFromApi() {
//		_isLoading.postValue(true)
		try {
			repository.getCurrencies().cachedIn(viewModelScope)
				.also {
					_response = it
				}
		} catch (e: Exception) {
			e.printStackTrace()
		}

//		_isLoading.postValue(false)
	}

	init {
		getCurrenciesFromApi()
	}

}