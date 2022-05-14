package jw.adamiak.currencycheck.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.Navigation
import com.squareup.moshi.*
import dagger.hilt.android.AndroidEntryPoint
import jw.adamiak.currencycheck.R
import jw.adamiak.currencycheck.ui.currencyList.CurrencyListViewModel

@AndroidEntryPoint
class CurrencyActivity : AppCompatActivity() {
	private val viewModel: CurrencyListViewModel by viewModels()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_currency)

		val navigation = Navigation.findNavController(this, R.id.nav_host_fragment)

	}

}