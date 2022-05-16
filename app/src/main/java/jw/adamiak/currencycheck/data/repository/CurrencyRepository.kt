package jw.adamiak.currencycheck.data.repository

import androidx.paging.PagingData
import jw.adamiak.currencycheck.data.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

	fun getCurrencies(): Flow<PagingData<Currency>>

}