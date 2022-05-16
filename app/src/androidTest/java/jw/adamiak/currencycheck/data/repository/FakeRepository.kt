package jw.adamiak.currencycheck.data.repository

import android.content.Context
import androidx.paging.PagingData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase.assertEquals
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.data.model.CurrencyDto
import jw.adamiak.currencycheck.utils.Helpers
import jw.adamiak.currencycheck.utils.Helpers.readJSONFile
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FakeRepository {
	private lateinit var moshi: Moshi
	private lateinit var adapter: JsonAdapter<CurrencyDto>

	private val currencies = mutableListOf<Currency>()
	lateinit var instrumentationContext: Context
	private var jsonString: String = "{\n" +
		"  \"base\": \"GBP\",\n" +
		"  \"date\": \"2013-12-24\",\n" +
		"  \"historical\": true,\n" +
		"  \"rates\": {\n" +
		"    \"CAD\": 1.739516,\n" +
		"    \"EUR\": 1.196476,\n" +
		"    \"UZS\": 1.636492,\n" +
		"    \"TZS\": 2412.926904,\n" +
		"    \"UAH\": 30.836128,\n" +
		"    \"UGX\": 3759.840175,\n" +
		"    \"USD\": 1.037818,\n" +
		"    \"UYU\": 43.485207,\n" +
		"    \"VEF\": 221916825978.82178,\n" +
		"    \"VND\": 23960.620083,\n" +
		"    \"VUV\": 118.586947,\n" +
		"    \"WST\": 2.676888,\n" +
		"    \"XAF\": 655.86086,\n" +
		"    \"XAG\": 0.04995,\n" +
		"    \"XAU\": 0.000571,\n" +
		"    \"XCD\": 2.804755,\n" +
		"    \"XDR\": 0.77939,\n" +
		"    \"XOF\": 655.810314,\n" +
		"    \"XPF\": 119.816062\n" +
		"  },\n" +
		"  \"success\": true,\n" +
		"  \"timestamp\": 1387929599\n" +
		"}"

	@Before
	fun setup() {
		moshi = Moshi.Builder()
			.add(Helpers.RatesAdapter())
			.add(KotlinJsonAdapterFactory())
			.build()
		adapter = moshi.adapter(CurrencyDto::class.java)

//		instrumentationContext = InstrumentationRegistry.getInstrumentation().context
//		jsonString = runBlocking { readJSONFile(instrumentationContext, "sample.json") }

	}

	// TODO: check if moshi translates object properly

	// TODO: check if there are no doubles in list -
	//  compare first and other elements

	// TODO: create test PagingAdapter with dummy data from JSON

	@Test
	fun received_object_is_equal_to_test_object() {
		val dto = adapter.fromJson(jsonString)
		dto?.let {
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

		val testObject1 = Currency(
			date = "2013-12-24",
			name = "CAD",
			rate = "1.739516"
		)

		assertEquals(currencies[0], testObject1)
	}





}