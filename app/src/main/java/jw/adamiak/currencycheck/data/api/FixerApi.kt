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

	companion object {
		const val API_KEY = "6RU8iv250T6xfT4QzytXvdP3fS4brKuv"
		const val BASE_URL = "https://api.apilayer.com"
	}

}