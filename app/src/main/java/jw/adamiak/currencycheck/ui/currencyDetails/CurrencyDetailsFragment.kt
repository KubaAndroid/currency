package jw.adamiak.currencycheck.ui.currencyDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jw.adamiak.currencycheck.R
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.databinding.FragmentCurrencyDetailsBinding

class CurrencyDetailsFragment: Fragment(R.layout.fragment_currency_details) {
	private val viewModel: CurrencyDetailsViewModel by viewModels()
	private var currencyArg: Currency? = null
	private lateinit var binding: FragmentCurrencyDetailsBinding
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentCurrencyDetailsBinding.bind(view)

		if(arguments != null && requireArguments().get("currency") != null) {
			currencyArg = requireArguments().get("currency") as Currency
			currencyArg?.let { currency ->
				viewModel.setCurrency(currency)
			}
		} else {
			findNavController().popBackStack()
		}

		binding.ivBack.setOnClickListener {
			findNavController().popBackStack()
		}

		viewModel.currency.observe(viewLifecycleOwner) {
			setupUiWithCurrency(it)
		}

	}

	private fun setupUiWithCurrency(currency: Currency) {
		binding.apply {
			tvCurrencyName.text = currency.name ?: ""
			tvCurrencyRate.text = currency.rate ?: ""
			tvCurrencyDate.text = getString(R.string.str_currency_date_state, currency.date)
		}
	}
}