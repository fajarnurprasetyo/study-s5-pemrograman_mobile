package `in`.blackant.study_chapter_5.adapter.holder

import android.view.ViewGroup
import `in`.blackant.study_chapter_5.model.table.DateCell
import java.text.SimpleDateFormat
import java.util.Locale

class DateCellViewHolder(parent: ViewGroup) : StringCellViewHolder(parent) {
    fun bind(item: DateCell?) {
        if (item == null) {
            super.bind("")
            return
        }

        val formatter = SimpleDateFormat("dd MMM yyyy", Locale(item.localeCode))
        super.bind(formatter.format(item.content))
    }
}