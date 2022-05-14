package jw.adamiak.currencycheck.ui.currencyList

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import jw.adamiak.currencycheck.R
import jw.adamiak.currencycheck.data.model.CurrencyDto
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.databinding.FragmentCurrencyListBinding
import jw.adamiak.currencycheck.utils.Helpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CurrencyListFragment: Fragment(R.layout.fragment_currency_list),
	CurrencyPagingAdapter.OnCurrencyListener {
	private val viewModel: CurrencyListViewModel by viewModels()

	private lateinit var binding: FragmentCurrencyListBinding
	private lateinit var pagingAdapter: CurrencyPagingAdapter
	private lateinit var linearLayoutManager: LinearLayoutManager

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentCurrencyListBinding.bind(view)

		setupObservers()
		setupUI()

	}

	private fun setupUI() {
		pagingAdapter = CurrencyPagingAdapter(requireContext(), this)
		linearLayoutManager = LinearLayoutManager(requireContext())
		binding.rvCurrencyList.apply {
			layoutManager = linearLayoutManager
			adapter = pagingAdapter
		}

	}


	private fun setupObservers() {
		viewModel.isLoading.observe(viewLifecycleOwner) {
			if (it) binding.pbCurrencyList.visibility = ProgressBar.VISIBLE
			else binding.pbCurrencyList.visibility = ProgressBar.INVISIBLE
		}

		viewModel.responsePaging?.observe(viewLifecycleOwner) {
			lifecycleScope.launch {
				pagingAdapter.submitData(it)
			}
		}

	}

	override fun onCurrencyClicked(currency: Currency) {
		val bundle = bundleOf("currency" to currency)
		findNavController().navigate(R.id.action_currencyListFragment_to_currencyDetailsFragment, bundle)
	}

}
