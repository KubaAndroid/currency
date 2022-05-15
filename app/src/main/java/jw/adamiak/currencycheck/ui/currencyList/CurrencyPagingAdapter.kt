package jw.adamiak.currencycheck.ui.currencyList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jw.adamiak.currencycheck.R
import jw.adamiak.currencycheck.data.model.Currency
import jw.adamiak.currencycheck.databinding.ItemCurrencyRateBinding

class CurrencyPagingAdapter(val context: Context, val listener: OnCurrencyListener) :
	PagingDataAdapter<Currency, CurrencyPagingAdapter.CurrencyViewHolder>
	(CurrencyDiffCallback()) {


	override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
		val currency = getItem(position)
		currency?.let {
			holder.bind(it)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
		return CurrencyViewHolder(
			ItemCurrencyRateBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	inner class CurrencyViewHolder(private val binding: ItemCurrencyRateBinding):
		RecyclerView.ViewHolder(binding.root) {
			fun bind(item: Currency){
				binding.apply {
					if(item.name.isNullOrEmpty() || item.rate.isNullOrEmpty()){
						tvCurrencyItemName.text = context.getString(R.string.str_currency_date, item.date)
						tvCurrencyItemRate.text = ""
					} else {
						root.setOnClickListener { listener.onCurrencyClicked(item) }
						tvCurrencyItemName.text = item.name ?: ""
						tvCurrencyItemRate.text = item.rate ?: ""
					}
				}
			}
		}

	interface OnCurrencyListener {
		fun onCurrencyClicked(currency: Currency)
	}

	private class CurrencyDiffCallback: DiffUtil.ItemCallback<Currency>() {
		override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
			return oldItem.hashCode() == newItem.hashCode()
		}

		override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
			return oldItem == newItem
		}
	}
}