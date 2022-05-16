package jw.adamiak.currencycheck.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.utils.Helpers.getDateString
import jw.adamiak.currencycheck.utils.Helpers.readJSONFile

class CurrencyPagingSource(private val api: FixerApi): PagingSource<Int, Currency>() {
	var minusDays = 0L
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Currency> {
		val currencyListObjects = mutableListOf<Currency>()
		val page = params.key ?: 0
		return try {
			val responseDto = api.getCurrencyRatesForDate(getDateString(minusDays)).body()
			responseDto?.let {
				currencyListObjects.add(Currency(date = it.date))
				for (rateName in it.rates.keys()){
					currencyListObjects.add(Currency(
							date = it.date,
							name = rateName ?: "",
							rate = String.format("%.8f", it.rates[rateName])
						)
					)
				}
			}
			minusDays += 1
			LoadResult.Page(
				data = currencyListObjects,
				prevKey = null,
				nextKey = if (page == 365) null else page + 1
			)

		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}

	override fun getRefreshKey(state: PagingState<Int, Currency>): Int? {
		return 0
	}

}