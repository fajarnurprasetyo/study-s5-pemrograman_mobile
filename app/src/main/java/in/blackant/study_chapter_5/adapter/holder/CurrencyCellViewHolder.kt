package `in`.blackant.study_chapter_5.adapter.holder

import android.view.ViewGroup
import `in`.blackant.study_chapter_5.model.table.CurrencyCell
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class CurrencyCellViewHolder(parent: ViewGroup) : StringCellViewHolder(parent) {
    private val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
    }

    fun bind(item: CurrencyCell?) {
        if (item == null) {
            super.bind("")
            return
        }

        formatter.currency = Currency.getInstance(item.localeCode)
        super.bind(formatter.format(item.content))
    }
}