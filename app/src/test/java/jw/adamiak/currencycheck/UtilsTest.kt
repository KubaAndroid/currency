package jw.adamiak.currencycheck

import junit.framework.TestCase.assertEquals
import jw.adamiak.currencycheck.utils.Helpers.getDateString
import org.junit.Test

class UtilsTest {

	// TODO: change date strings
	private val dateToday = "2022-05-18"
	private val dateYesterday = "2022-05-17"
	private val dateYearAgo = "2021-05-18"

	@Test
	fun check_today_date(){
		val date = getDateString(0)
		assertEquals(date, dateToday)
	}

	@Test
	fun check_yesterday_date(){
		val date = getDateString(1)
		assertEquals(date, dateYesterday)
	}

	@Test
	fun check_year_ago_date(){
		val date = getDateString(365)
		assertEquals(date, dateYearAgo)
	}

}