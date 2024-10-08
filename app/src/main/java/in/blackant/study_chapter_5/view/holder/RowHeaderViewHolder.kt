package `in`.blackant.study_chapter_5.view.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import `in`.blackant.study_chapter_5.databinding.TableRowHeaderBinding
import `in`.blackant.study_chapter_5.model.table.RowHeader

class RowHeaderViewHolder(
    parent: ViewGroup,
    private val binding: TableRowHeaderBinding = TableRowHeaderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ),
) : AbstractViewHolder(binding.root) {
    fun bind(row: RowHeader?) {
        binding.text.text = row?.content.toString()
    }
}
