package jw.adamiak.currencycheck.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.data.model.CurrencyDto
import jw.adamiak.currencycheck.ui.currencyList.CurrencyListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Buffer
import org.json.JSONArray
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
		val date = LocalDateTime.now().minusDays(minusDate)
		val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		return date.format(formatter)
	}

	fun toggleProgressBar(pb: ProgressBar, show: Boolean){
		CoroutineScope(Dispatchers.Main).launch {
			if(show){
				pb.visibility = View.VISIBLE
			} else {
				pb.visibility = View.INVISIBLE
			}
		}
	}

	private fun findDatesInJsonString(s: String): Sequence<MatchResult> {
		val dateRegex = """[\d]{4}-[\d]{2}-[\d]{2}""".toRegex()
		return dateRegex.findAll(s)
	}


	class RatesAdapter {
		@FromJson
		fun fromJson(jsonReader: JsonReader): JSONObject? {
			return (jsonReader.readJsonValue() as? Map<String, JSONArray>)?.let { data ->
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