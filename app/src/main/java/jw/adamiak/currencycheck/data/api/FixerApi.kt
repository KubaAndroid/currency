package jw.adamiak.currencycheck.data.api

import jw.adamiak.currencycheck.data.model.CurrencyDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerApi {

	@GET("fixer/{date}")
	suspend fun getCurrencyRatesForDate(
		@Path("date") date: String,
		@Query("apikey") apiKey: String = API_KEY
	): Response<CurrencyDto>

	@GET("fixer/timeseries")
	suspend fun getCurrencyRates(
		@Query("end_date") endDate: String,
		@Query("start_date") startDate: String,
		@Query("apikey") apiKey: String = API_KEY
	): Response<Any>




	companion object {
		const val API_KEY = "GNijUgQ6O6PZnbOpSarpAzEiDEM9odw3"
		const val BASE_URL = "https://api.apilayer.com"
	}

}