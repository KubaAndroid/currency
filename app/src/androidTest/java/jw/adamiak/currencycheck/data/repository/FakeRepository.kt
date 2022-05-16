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
	lateinit var instrumentationContext: Context

	private val currencies = mutableListOf<Currency>()
	private var jsonString: String = ""

	@Before
	fun setup() {
		moshi = Moshi.Builder()
			.add(Helpers.RatesAdapter())
			.add(KotlinJsonAdapterFactory())
			.build()
		adapter = moshi.adapter(CurrencyDto::class.java)

		instrumentationContext = InstrumentationRegistry.getInstrumentation().targetContext
		jsonString = runBlocking { readJSONFile(instrumentationContext, "sample4.json") }

		// TODO: put all objects into list here?
	}

	@Test
	fun received_object_is_equal_to_test_object() {
		val dto = adapter.fromJson(jsonString)
		dto?.let {
			for (rateName in it.rates.keys()) {
				currencies.add(
					Currency(
						date = it.date,
						name = rateName,
						rate = it.rates[rateName].toString()
					)
				)
			}
		}

		val sampleObject = Currency(
			date = "2022-05-16",
			name = "AED",
			rate = "3.831148"
		)

		assertEquals(currencies[0], sampleObject)
	}

	// TODO: check if there are no doubles in list -
	//  compare first and other elements
	@Test
	fun received_objects_dont_double() {
		val dto = adapter.fromJson(jsonString)
		dto?.let {
			for (rateName in it.rates.keys()) {
				currencies.add(
					Currency(
						date = it.date,
						name = rateName,
						rate = it.rates[rateName].toString()
					)
				)
			}
		}
		var duplicates = false
		val firstCurrencyFromTheList = currencies[0]
		for(currency in currencies.subList(1, currencies.size - 1)) {
			if (currency.equals(firstCurrencyFromTheList)){
				duplicates = true
			}
		}
		assertEquals(duplicates, false)
	}

	// TODO: create test PagingAdapter with dummy data from JSON



}