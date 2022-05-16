package jw.adamiak.currencycheck

import junit.framework.TestCase.assertEquals
import jw.adamiak.currencycheck.utils.Helpers.getDateString
import org.junit.Test

class UtilsTest {


	@Test
	fun check_today_date(){
		// TODO: change date string to today
		val date = getDateString(0)
		val dateToday = "2022-05-16"
		assertEquals(date, dateToday)
	}

	@Test
	fun check_yesterday_date(){
		val date = getDateString(1)
		val dateToday = "2022-05-15"
		assertEquals(date, dateToday)
	}



}