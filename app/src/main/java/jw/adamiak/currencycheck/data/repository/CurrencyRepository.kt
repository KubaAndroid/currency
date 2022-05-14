package jw.adamiak.currencycheck.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import jw.adamiak.currencycheck.data.api.CurrencyPagingSource
import jw.adamiak.currencycheck.data.api.FixerApi
import jw.adamiak.currencycheck.data.model.Currency
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject


class CurrencyRepository @Inject constructor(
	private val api: FixerApi
) {

	fun getCurrencies(): Flow<PagingData<Currency>> {
		return Pager(
			config = PagingConfig(enablePlaceholders = false, pageSize = 1),
			pagingSourceFactory = { CurrencyPagingSource(api) }
		).flow
	}
}