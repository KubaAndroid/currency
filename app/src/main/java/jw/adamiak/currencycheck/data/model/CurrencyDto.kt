package jw.adamiak.currencycheck.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class CurrencyDto(
    @Json(name = "base")
    val base: String,
    @Json(name = "date")
    val date: String,
    @Json(name = "historical")
    val historical: Boolean,
    @Json(name = "rates")
    val rates: JSONObject,
    @Json(name = "success")
    val success: Boolean
)