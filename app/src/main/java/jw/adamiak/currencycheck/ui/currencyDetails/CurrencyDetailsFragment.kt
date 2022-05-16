package jw.adamiak.currencycheck.ui.currencyDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jw.adamiak.currencycheck.R
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.databinding.FragmentCurrencyDetailsBinding

class CurrencyDetailsFragment: Fragment(R.layout.fragment_currency_details) {
	private var currency: Currency? = null
	private lateinit var binding: FragmentCurrencyDetailsBinding
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentCurrencyDetailsBinding.bind(view)

		if(arguments != null) {
			currency = requireArguments().get("currency") as Currency
			currency?.let {
				binding.tvCurrencyName.text = it.name ?: ""
				binding.tvCurrencyRate.text = it.rate ?: ""
				binding.tvCurrencyDate.text = getString(R.string.str_currency_date_state, it.date)
			}
		} else {
			findNavController().popBackStack()
		}

		binding.ivBack.setOnClickListener {
			findNavController().popBackStack()
		}

	}
}