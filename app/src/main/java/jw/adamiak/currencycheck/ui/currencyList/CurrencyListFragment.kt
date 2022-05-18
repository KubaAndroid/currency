package jw.adamiak.currencycheck.ui.currencyList

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jw.adamiak.currencycheck.R
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.databinding.FragmentCurrencyListBinding
import jw.adamiak.currencycheck.utils.Helpers.toggleProgressBar
import kotlinx.coroutines.*

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

		setupUI()
		setupObservers()
	}

	private fun setupUI() {
		pagingAdapter = CurrencyPagingAdapter(requireContext(), this)
		linearLayoutManager = LinearLayoutManager(requireContext())
		binding.rvCurrencyList.apply {
			layoutManager = linearLayoutManager
			adapter = pagingAdapter
		}
		setupAdapter()
	}

	private fun setupObservers() {
		viewModel.response?.observe(viewLifecycleOwner) {
			lifecycleScope.launch {
				pagingAdapter.submitData(it)
			}
		}
	}

	private fun setupAdapter(){
		pagingAdapter.addLoadStateListener { loadState ->
			binding.tvCurrencyListEmpty.isVisible = pagingAdapter.itemCount < 1
			if(loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
				toggleProgressBar(binding.pbCurrencyList, true)
			}
			if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
				toggleProgressBar(binding.pbCurrencyList, true)
			} else {
				toggleProgressBar(binding.pbCurrencyList, false)
			}
		}
	}

	override fun onCurrencyClicked(currency: Currency) {
		val bundle = bundleOf("currency" to currency)
		findNavController().navigate(R.id.action_currencyListFragment_to_currencyDetailsFragment, bundle)
	}

}
