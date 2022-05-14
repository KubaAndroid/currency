package jw.adamiak.currencycheck.ui.currencyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.databinding.ItemCurrencyRateBinding

class CurrencyListAdapter(val listener: OnCurrencyClickListener): ListAdapter<Currency,
	CurrencyListAdapter.CurrencyListViewHolder>(CurrencyDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
		return CurrencyListViewHolder(
			ItemCurrencyRateBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
		if (getItem(position) != null) {
			holder.bind(getItem(position))
		}
	}

	inner class CurrencyListViewHolder(private val binding: ItemCurrencyRateBinding):
		RecyclerView.ViewHolder(binding.root) {
		fun bind(item: Currency) {
			binding.apply {
				if(item.name.isNullOrEmpty() || item.rate.isNullOrEmpty()) {
					tvCurrencyItemName.text = item.date
					tvCurrencyItemRate.text = ""
				} else {
					tvCurrencyItemName.text = item.name
					tvCurrencyItemRate.text = item.rate
					clCurrencyItem.setOnClickListener {
						listener.onClick(item)
					}
				}
			}
		}
	}

	interface OnCurrencyClickListener {
		fun onClick(item: Currency)
	}

	// TODO: get filter - for filtering currencies
}

class CurrencyDiffCallback: DiffUtil.ItemCallback<Currency>() {
	override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
		return oldItem.hashCode() == newItem.hashCode()
	}

	override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
		return oldItem == newItem
	}
}