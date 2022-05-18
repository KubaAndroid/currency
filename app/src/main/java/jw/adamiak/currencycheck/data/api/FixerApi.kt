package jw.adamiak.currencycheck.data.api

import jw.adamiak.currencycheck.data.model.CurrencyDto
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

	companion object {
		const val API_KEY = "jrqOgNVlW0DYS0IXbz36oSx0NZD1E4T3"
		const val BASE_URL = "https://api.apilayer.com"
	}

}