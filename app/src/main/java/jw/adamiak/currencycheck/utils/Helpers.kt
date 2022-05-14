package jw.adamiak.currencycheck.utils

import android.content.Context
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.data.model.CurrencyDto
import jw.adamiak.currencycheck.ui.currencyList.CurrencyListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Helpers {

	suspend fun  readJSONFile(context: Context, filename: String): String  = withContext(Dispatchers.IO){
		return@withContext try {
			val inputStream: InputStream = context.assets.open(filename)
			val size: Int = inputStream.available()
			val buffer = ByteArray(size)
			inputStream.read(buffer)
			val jsonString = String(buffer)
			jsonString
		} catch (e: IOException) {
			e.printStackTrace()
			""
		}
	}

	fun getDateString(minusDate: Long): String {
		// TODO: int -> long conversion goes here
		val date = LocalDateTime.now().minusDays(minusDate)
		val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		println("getDateString minus $minusDate: ${date.format(formatter)}")
		return date.format(formatter)
	}

	private fun findDatesInJsonString(s: String): Sequence<MatchResult> {
		val dateRegex = """[\d]{4}-[\d]{2}-[\d]{2}""".toRegex()
		return dateRegex.findAll(s)
	}


	class RatesAdapter {
		@FromJson
		fun fromJson(reader: JsonReader): JSONObject? {
			return (reader.readJsonValue() as? Map<String, List<Currency>>)?.let { data ->
				try {
					JSONObject(data)
				} catch (e: JSONException) {
					e.printStackTrace()
					null
				}
			}
		}

		@ToJson
		fun toJson(writer: JsonWriter, value: JSONObject?) {
			value?.let { writer.value(Buffer().writeUtf8(value.toString())) }
		}

		@ToJson
		fun toJson(rate: Currency): String {
			return "date=${rate.date}, rates={${rate.name}=${rate.rate}}"
		}
	}

	suspend fun moshiTest(json: String) = withContext(Dispatchers.IO) {
		val moshi = Moshi.Builder()
			.add(RatesAdapter())
			.add(KotlinJsonAdapterFactory())
			.build()

		val currencyDtoAdapter = moshi.adapter(CurrencyDto::class.java)
		val dto = currencyDtoAdapter.fromJson(json)
		println("dto: $dto")

		val currencies = mutableListOf<Currency>()
		dto?.let {
			currencies.add(Currency(date = it.date))
			for (rateName in it.rates.keys()) {
				currencies.add(
					Currency(
						date = it.date,
						name = rateName ?: "",
						rate = it.rates[rateName].toString()
					)
				)
			}
		}
	}



}