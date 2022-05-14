package jw.adamiak.currencycheck.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
	val date: String,
	val name: String? = "",
	val rate: String? = ""
): Parcelable