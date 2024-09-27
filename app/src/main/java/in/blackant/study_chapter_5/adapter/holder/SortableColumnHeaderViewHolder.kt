package `in`.blackant.study_chapter_5.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder
import `in`.blackant.study_chapter_5.databinding.TableColumnHeaderBinding
import `in`.blackant.study_chapter_5.model.table.ColumnHeader

class SortableColumnHeaderViewHolder(
    parent: ViewGroup,
    private val binding: TableColumnHeaderBinding = TableColumnHeaderBinding.inflate(
        LayoutInflater.from(
            parent.context
        )
    ),
) : AbstractSorterViewHolder(binding.root) {
    fun bind(column: ColumnHeader?) {
        binding.text.text = column?.content as String?
    }
}