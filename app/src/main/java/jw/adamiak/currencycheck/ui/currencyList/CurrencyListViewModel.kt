package jw.adamiak.currencycheck.ui.currencyList

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
	private val repository: CurrencyRepository
): ViewModel() {

	private var _response = MutableLiveData<List<Currency>>()
	val response: LiveData<List<Currency>>
		get() = _response

	private var _responsePaging: Flow<PagingData<Currency>>? = null
	val responsePaging: LiveData<PagingData<Currency>>?
		get() = _responsePaging?.asLiveData()

	private var _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean>
		get() = _isLoading

	fun getCurrenciesFromApi(): Flow<PagingData<Currency>> {
		_isLoading.postValue(true)
		val result = repository.getCurrencies().cachedIn(viewModelScope)
		_responsePaging = result
		_isLoading.postValue(false)
		return result
	}


	// TODO: set time span for currencies search
	fun setLoading(isload: Boolean) {
		viewModelScope.launch {
			_isLoading.value = isload
		}
	}


	init {
		println("CurrencyListViewModel init")
		getCurrenciesFromApi()
	}

}