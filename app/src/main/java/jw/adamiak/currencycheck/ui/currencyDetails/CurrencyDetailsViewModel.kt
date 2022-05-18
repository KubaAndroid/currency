package jw.adamiak.currencycheck.ui.currencyDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jw.adamiak.currencycheck.data.model.Currency

class CurrencyDetailsViewModel: ViewModel() {
	private var _currency = MutableLiveData<Currency>()
	val currency: LiveData<Currency>
		get() = _currency

	fun setCurrency(currencyToSet: Currency) {
		_currency.postValue(currencyToSet)
	}
}