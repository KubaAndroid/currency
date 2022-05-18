package jw.adamiak.currencycheck.repository

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase.assertEquals
import jw.adamiak.currencycheck.data.CurrencyPagingSource
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.data.model.CurrencyDto
import jw.adamiak.currencycheck.utils.Helpers
import jw.adamiak.currencycheck.utils.Helpers.readJsonFile
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class FakeRepositoryTest {
	private lateinit var moshi: Moshi
	private lateinit var adapter: JsonAdapter<CurrencyDto>
	private lateinit var instrumentationContext: Context

	private val currencies = mutableListOf<Currency>()
	private var apiReplyJsonString: String = ""

	@Before
	fun setup() {
		moshi = Moshi.Builder()
			.add(Helpers.RatesAdapter())
			.add(KotlinJsonAdapterFactory())
			.build()
		adapter = moshi.adapter(CurrencyDto::class.java)
		instrumentationContext = InstrumentationRegistry.getInstrumentation().targetContext
		apiReplyJsonString = runBlocking { readJsonFile(instrumentationContext, "sample4.json") }
		dtoToDomainObjects()
	}

	@Test
	fun received_object_is_equal_to_sample_object() {
		val sampleObject = Currency(
			date = "2022-05-16",
			name = "AED",
			rate = "3.831148"
		)
		assertEquals(currencies[0], sampleObject)
	}


	@Test
	fun received_objects_dont_double() {
		val random = Random.nextInt(0, currencies.size)
		val randomCurrencyFromTheList = currencies[random]
		val numberOfOccurrences = currencies.filter {
			it.date == randomCurrencyFromTheList.date &&
				it.name == randomCurrencyFromTheList.name &&
				it.rate == randomCurrencyFromTheList.rate
		}.size
		assertEquals(1, numberOfOccurrences)
	}

	@Test
	fun check_if_dates_are_the_same() {
		val random = Random.nextInt(0, currencies.size)
		val sampleDate = currencies[random].date
		val numberOfOccurrences = currencies.filter {
			it.date == sampleDate
		}.size
		assertEquals(currencies.size, numberOfOccurrences)
	}


	private fun dtoToDomainObjects(){
		val dto = adapter.fromJson(apiReplyJsonString)
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
	}


}